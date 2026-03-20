package com.green.greenuni.application.major;

import com.green.greenuni.application.course.model.CourseListMaxPageReq;
import com.green.greenuni.application.major.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MajorService {
    private final MajorMapper majorMapper;

    public List<MajorListForCreateRes> listForCreate(){ return majorMapper.listForCreate(); }

    public List<MajorListRes> getMajorList(MajorListMaxPageReq req){
        return majorMapper.getMajorList(req);
    }

    public int createMajor(MajorCreateReq req){
        return majorMapper.createMajor(req);
    }

    public int modifyMajor(MajorModReq req){
        return majorMapper.modifyMajor(req);
    }

    public MajorDetailRes getMajor(long majorId) {
        return majorMapper.getMajor(majorId);
    }

    public List<ProfessorListDto> getProfessorList(){
        return majorMapper.getProfessorList();
    }

    public Map<String, Object> getMajorMaxPage(MajorListMaxPageReq req){ return majorMapper.findMaxPage(req); }
}
