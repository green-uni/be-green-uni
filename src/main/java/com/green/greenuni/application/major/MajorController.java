package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.*;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResultResponse<Integer> createMajor(@RequestBody MajorCreateReq req){
        int result = majorService.createMajor(req);
        return new ResultResponse<>("학과 등록 완료", result);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping
    public ResultResponse<Integer> modifyMajor(@RequestBody MajorModReq req){
        int result = majorService.modifyMajor(req);
        return new ResultResponse<>("학과 수정 완료", result);
    }

    @GetMapping("/{majorId}")
    public ResultResponse<MajorDetailRes> getMajor(@PathVariable long majorId) {
        MajorDetailRes res = majorService.getMajor(majorId);
        return new ResultResponse<>("학과 상세 조회", res);
    }
}
