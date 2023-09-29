package com.prizy.product.service;

import com.prizy.product.service.impl.IdealPricingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class IdealPricingServiceTest {

    @InjectMocks
    private IdealPricingServiceImpl idealPricingService;

    @Test
    public void testCompute1() {
        List<Double> priceList = Arrays.asList(2199.0, 2097.5, 2149.5, 2219.0,
                2167.0, 2233.5, 2179.5, 2089.5, 2068.0, 2283.5);

        Double result = idealPricingService.compute(priceList);
        assertEquals(2602, result.intValue());
    }

    @Test
    public void testCompute2() {
        List<Double> priceList = Arrays.asList(2068.0, 2089.5, 2097.5, 2149.5,
                2167.0, 2179.5, 2199.0, 2219.0, 2233.5, 2283.5);

        Double result = idealPricingService.compute(priceList, true);
        assertEquals(2602, result.intValue());
    }
}
