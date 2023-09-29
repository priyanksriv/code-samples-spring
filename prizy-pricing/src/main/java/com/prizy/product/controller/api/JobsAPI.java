package com.prizy.product.controller.api;

import com.prizy.product.vo.JobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "Jobs", produces =  "application/json")
public interface JobsAPI {

    @ApiOperation(value = "Trigger a price calculation command.", notes = "Triggers a price calculation command.",
            response = Void.class, tags = { "job" }, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Price calculation command accepted", response = JobVO.class),
            @ApiResponse(code = 202, message = "Bad command request", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<JobVO> startPriceCalculatorJob(String command);
}
