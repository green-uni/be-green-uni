package com.green.greenuni.application.member;

import com.green.greenuni.application.admin.model.*;
import com.green.greenuni.application.member.model.*;
import com.green.greenuni.configuration.util.MyFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final MyFileUtil myFileUtil;
    MemberCreateRes res = new MemberCreateRes();

    @Transactional
    public MemberCreateRes createMember(MemberCreateReq req, MultipartFile mf){
        //파일 업로드가 되었으면 저장하는 파일명을 테이블에 저장
        String savedPicFileName = mf == null ? null : myFileUtil.makeRandomFileName(mf);
        req.setPic(savedPicFileName);

        // member table insert
        memberMapper.createMember(req);

        // 프로파일 이미지 저장
        if( mf != null ){ //이미지가 업로드 되었다면
            long id = req.getMemberId();
            String middlePath = "member/" + id;
            // 디렉토리 생성
            myFileUtil.makeFolders(middlePath);

            String fullFilePath = String.format("%s/%s", middlePath, savedPicFileName);
            try{
                myFileUtil.transferTo(mf, fullFilePath);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // 멤버코드: 입학연도(4자리) + 구분코드(1자리) + 순번(3자리)

        // 입학연도
        String entryYear = req.getEntryDate().substring(0,4);

        // role에 따른 구분코드
        String roleNum;
        switch (req.getRole()){
            case "admin"     -> roleNum = "3";
            case "professor" -> roleNum = "2";
            default          -> roleNum = "1";
        }

        // 멤버코드 생성
        String code = entryYear + roleNum + String.format("%03d", req.getMemberId());
        req.setCode(code);

        // 생일을 초기 비밀번호
        String rawPw = req.getBirth().replace("-", ""); //- 제거
        String hashedPw = passwordEncoder.encode(rawPw);
        req.setPassword(hashedPw);

        // 멤버코드와 비밀번호 삽입
        memberMapper.updateMemberCodeAPw(req);

        switch (req.getRole()){
            case "admin"     -> memberMapper.createStaff(req);
            case "professor" -> memberMapper.createProfessor(req);
            default          -> memberMapper.createStudent(req);
        }

        res.setMemberCode(req.getCode());
        res.setMemberRole(req.getRole());
        return res;
    }

    // 모든 멤버 조회
    public List<MemberListRes> findAllMember(MemberListReq req){
        return memberMapper.findAllMember(req);
    }
    // 모든 멤버 목록 조회때 최대 페이지 조회
    public int getMemberMaxPage(MemberListMaxPageReq req){ return memberMapper.findMaxPage(req); }


    public MemberLoginRes logIn(MemberLoginReq req){
        MemberFindByCodeRes res = memberMapper.findByCode( req.getCode() );
        if (res == null) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        } // 로그인 code를 DB에서 조회 후 결과가 없으면 해당 문구 throw
        if(!passwordEncoder.matches( req.getPassword(), res.getPassword() ) ){
            return null;
        }
        return MemberLoginRes.builder()
                .loginUserId( res.getMemberId() )
                .code( res.getCode() )
                .name( res.getName() )
                .role( res.getRole() )
                .stdMajorName( res.getStdMajorName() )
                .profMajorName( res.getProfMajorName() )
                .build();
    }

    // 현재 로그인한 사람의 프로파일 데이터 가져오기
    public MemberProfileRes findLoginUserProfile( long id, String role ){
        return memberMapper.findUserProfile( id );
    }


    // 로그인 유저가 본인 프로파일 정보 수정
    @Transactional
    public String modMemberBySelf(long loginUserId, String loginUserRole, MemberEditReq req, MultipartFile pic) {
        req.setLoginUserId(loginUserId);
        // 프로파일 사진 수정
        //기존 프로파일 사진은 삭제, 기존 파일명을 구해야 함.
        MemberProfileRes res = memberMapper.findUserProfile( loginUserId );
        String savedPic = res.getPic();

        String folderPath = String.format("member/%d", loginUserId);

        if (pic != null) {
            if (res.getPic() != null) {
                myFileUtil.deleteFile(String.format("%s/%s", folderPath, res.getPic()));
            }
            savedPic = myFileUtil.makeRandomFileName(pic);
            try {
                myFileUtil.transferTo(pic, String.format("%s/%s", folderPath, savedPic));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        req.setPic(savedPic);
        memberMapper.modMemberBySelf(req);

        if("professor".equals(loginUserRole)){
            memberMapper.modProfessorMySelf(req);
        }

        return savedPic;
    }

    // 관리자가 목록에서 상태만 수정
    @Transactional
    public int modStatusList(List<MemberEditListReq> reqs){
        for(MemberEditListReq req : reqs){
            switch (req.getRole()){
                case "admin"     -> memberMapper.modStfStatus(req);
                case "professor" -> memberMapper.modProfStatus(req);
                default          -> memberMapper.modStdStatus(req);
            }
        }
        return 1;
    }

    // 관리자가 수정
//    @Transactional
//    public int editMemberByAdmin(MemberEditByAdminReq req){
//        memberMapper.editMemberByAdmin(req);
//        switch (req.getRole()){
//            case "admin"     -> memberMapper.editStfByAdmin(req);
//            case "professor" -> memberMapper.editProfByAdmin(req);
//            default          -> memberMapper.editStdByAdmin(req);
//        }
//        return 1;
//    }

    //비밀번호 수정
    @Transactional
    public int changePw(long id, MemberPwChangeReq req){
        req.setLoginUserId(id);
        MemberFindByIdRes res = memberMapper.findById( id );

        if(!passwordEncoder.matches( req.getOldPassword(), res.getPassword() ) ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 변경할 비밀번호 암호화
        String rawPw = req.getNewPassword();
        String hashedPw = passwordEncoder.encode(rawPw);
        req.setNewPassword(hashedPw);

        return memberMapper.changePw(req);
    }
}