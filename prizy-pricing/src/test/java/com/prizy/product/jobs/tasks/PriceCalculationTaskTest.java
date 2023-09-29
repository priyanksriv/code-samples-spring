package com.prizy.product.jobs.tasks;

import com.prizy.product.domain.entity.PricingDetails;
import com.prizy.product.domain.repository.PricingDetailsRepository;
import com.prizy.product.service.PricingService;
import com.prizy.product.service.impl.IdealPricingServiceImpl;
import com.prizy.store.service.StorePriceService;
import com.prizy.store.service.impl.StorePriceServiceImpl;
import com.prizy.store.vo.StorePriceVO;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PriceCalculationTaskTest {

    private PriceCalculationTask priceCalculationTask;

    private PricingService pricingService;
    private StorePriceService storePriceService;
    private PricingDetailsRepository pricingDetailsRepository;

    @Before
    public void setUp() {
        priceCalculationTask = new PriceCalculationTask("");
        pricingService = mock(IdealPricingServiceImpl.class);
        pricingDetailsRepository = mock(PricingDetailsRepository.class);
        storePriceService = mock(StorePriceServiceImpl.class);

        priceCalculationTask.setPricingService(pricingService);
        priceCalculationTask.setPricingDetailsRepository(pricingDetailsRepository);
        priceCalculationTask.setStorePriceService(storePriceService);
    }

    @Test
    public void testRunMethod() {
        priceCalculationTask.run();
        verify(storePriceService, times(1)).findByProductId(anyString());
    }

    @Test
    public void testExtractPriceList() {
        StorePriceVO vo = new StorePriceVO();
        vo.setPrice(2019.0);

        List<StorePriceVO> vos = Collections.singletonList(vo);
        List<Double> priceList = Collections.singletonList(2019.0);

        List<Double> result = priceCalculationTask.extractPriceList(vos);
        assertEquals(result.get(0), priceList.get(0));
    }

    @Test
    public void testComputeAndSavePricingDetails() {
        priceCalculationTask.computeAndSavePricingDetails(Collections.emptyList());
        verify(pricingDetailsRepository, times(1)).save(any(PricingDetails.class));
    }

    @Test
    public void testComputePricingDetails() {
        List<Double> priceList = Arrays.asList(2068.0, 2089.5, 2097.5, 2149.5,
                2167.0, 2179.5, 2199.0, 2219.0, 2233.5, 2283.5);

        PricingDetails pricingDetails = new PricingDetails();
        pricingDetails.setAveragePrice(2168.6);
        pricingDetails.setHighestPrice(2283.5);
        pricingDetails.setLowestPrice(2068.0);
        pricingDetails.setIdealPrice(2602.0);

        when(pricingService.compute(priceList, true)).thenReturn(2602.0);

        PricingDetails result = priceCalculationTask.computePricingDetails(priceList);

        assertEquals(result.getIdealPrice(), pricingDetails.getIdealPrice());
        assertEquals(result.getAveragePrice(), pricingDetails.getAveragePrice());
        assertEquals(result.getHighestPrice(), pricingDetails.getHighestPrice());
        assertEquals(result.getLowestPrice(), pricingDetails.getLowestPrice());
    }
}
