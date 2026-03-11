package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.MajorCreateReq;
import com.green.greenuni.application.major.model.MajorListRes;
import com.green.greenuni.application.major.model.MajorListForCreateRes;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/major")
@RequiredArgsConstructor
public class MajorController {
    private final MajorService majorService;

    @GetMapping("/create")
    public ResultResponse<?> listForCreate(){ //Member, Lecture 생성할 때 필요한 전공 목록
        List<MajorListForCreateRes> list = majorService.listForCreate();
        return new ResultResponse<>("학과 목록 가나다순", list);
    }

    @GetMapping
    public ResultResponse<?> getMajorList(){
        List<MajorListRes> list = majorService.getMajorList();
        return new ResultResponse<>("학과 리스트", list);
    }

    @PostMapping
    public ResultResponse<Integer> createMajor(@RequestBody MajorCreateReq req){
        int result = majorService.createMajor(req);
        return new ResultResponse<>("학과 등록 완료", result);
    }
}
