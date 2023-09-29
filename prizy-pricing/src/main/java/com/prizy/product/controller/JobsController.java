package com.prizy.product.controller;

import com.prizy.product.controller.api.JobsAPI;
import com.prizy.product.service.JobsService;
import com.prizy.product.vo.JobVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "api/v1/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobsController implements JobsAPI {

    @PostMapping(value = "/priceCalculator", params = { "command" })
    @Override
    public ResponseEntity<JobVO> startPriceCalculatorJob(@RequestParam String command) {
        JobVO jobVO = jobsService.startPriceCalculatorJob(command);
        return ResponseEntity.accepted().body(jobVO);
    }

    @Autowired
    private JobsService jobsService;
}
