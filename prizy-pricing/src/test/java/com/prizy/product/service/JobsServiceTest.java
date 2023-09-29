package com.prizy.product.service;

import com.prizy.common.exception.ApiException;
import com.prizy.common.exception.BadRequestException;
import com.prizy.product.jobs.PriceCalculationJob;
import com.prizy.product.service.impl.JobsServiceImpl;
import com.prizy.product.vo.JobVO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobsServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private JobsServiceImpl jobsService;

    @Mock
    private PriceCalculationJob priceCalculationJob;

    @Test
    public void testStartCalculatorJob_JobAlreadyRunning() {

        when(priceCalculationJob.calculatePrices()).thenReturn(-1L);
        thrown.expect(ApiException.class);
        thrown.expectMessage("Another job is already running.");

        jobsService.startPriceCalculatorJob("start");
    }

    @Test
    public void testStartCalculatorJob_Success() {
        long time = System.currentTimeMillis();
        JobVO jobVO = new JobVO();
        jobVO.setJob("price-calculation");
        jobVO.setStarted(new Date(time));

        when(priceCalculationJob.calculatePrices()).thenReturn(time);
        JobVO result = jobsService.startPriceCalculatorJob("start");

        assertEquals(result.getJob(), jobVO.getJob());
        assertEquals(result.getStarted(), result.getStarted());
    }

    @Test
    public void testStartCalculatorJob_BadRequest() {

        thrown.expect(BadRequestException.class);
        thrown.expectMessage("Invalid command.");

        jobsService.startPriceCalculatorJob("go");
    }
}
