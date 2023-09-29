package com.prizy.product.jobs;

import com.prizy.product.jobs.tasks.PriceCalculationTask;
import com.prizy.product.service.ProductService;
import com.prizy.product.vo.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PriceCalculationJobTest {

    @InjectMocks
    private PriceCalculationJob priceCalculationJob;

    @Mock
    private ApplicationContext context;

    @Mock
    private ProductService productService;

    @Mock
    private ExecutorService taskExecutor;


    @Test
    public void testCalculate() {
        List<ProductVO> productVOS = new ArrayList<>();
        ProductVO productVO = new ProductVO();
        productVO.setId(UUID.randomUUID().toString());
        productVOS.add(productVO);

        PriceCalculationTask priceCalculationTask = new PriceCalculationTask("");

        when(productService.findAllProducts()).thenReturn(productVOS);
        when(context.getBean(anyString(), anyString())).thenReturn(priceCalculationTask);

        priceCalculationJob.calculate();
        verify(taskExecutor, times(1)).execute(priceCalculationTask);
    }

}
