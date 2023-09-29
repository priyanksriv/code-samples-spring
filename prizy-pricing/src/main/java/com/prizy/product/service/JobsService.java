package com.prizy.product.service;

import com.prizy.product.vo.JobVO;

public interface JobsService {

    JobVO startPriceCalculatorJob(String command);
}
