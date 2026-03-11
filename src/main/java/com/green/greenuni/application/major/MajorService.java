package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.MajorCreateReq;
import com.green.greenuni.application.major.model.MajorCreateRes;
import com.green.greenuni.application.major.model.MajorListForCreateRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MajorService {
    private final MajorMapper majorMapper;

    public List<MajorListForCreateRes> listForCreate(){ return majorMapper.listForCreate(); }

    public List<MajorCreateRes> getMajorList(){
        return majorMapper.getMajorList();
    }

    public int createMajor(MajorCreateReq req){
        return majorMapper.createMajor(req);
    }
}
