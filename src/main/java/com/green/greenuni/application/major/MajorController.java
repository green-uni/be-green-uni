package com.green.greenuni.application.major;

import com.green.greenuni.application.major.model.MajorListRes;
import com.green.greenuni.configuration.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/major")
@RequiredArgsConstructor
public class MajorController {
    private final MajorService majorService;

    @GetMapping
    public ResultResponse<?> ListUpMajor(){
        List<MajorListRes> list = majorService.ListUpMajor();
        return new ResultResponse<>("전공 목록 가나다순", list);
    }
}
