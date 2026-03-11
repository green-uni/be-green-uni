package com.green.greenuni.application.member;

import com.green.greenuni.application.member.model.MemberCreateReq;
import com.green.greenuni.application.member.model.MemberCreateRes;
import com.green.greenuni.application.member.model.MemberListReq;
import com.green.greenuni.application.member.model.MemberListRes;
import com.green.greenuni.configuration.util.MyFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    public List<MemberListRes> findAllMember(MemberListReq req){
        return memberMapper.findAllMember(req);
    }
}