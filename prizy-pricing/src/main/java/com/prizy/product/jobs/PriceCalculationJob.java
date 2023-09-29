package com.prizy.product.jobs;

import com.prizy.product.jobs.tasks.PriceCalculationTask;
import com.prizy.product.service.ProductService;
import com.prizy.product.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class PriceCalculationJob {

    private Logger LOG = LoggerFactory.getLogger(PriceCalculationJob.class);

    private ExecutorService taskExecutor, jobExecutor;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    @PostConstruct
    public void init() {
        taskExecutor = Executors.newFixedThreadPool(threadCount);
        jobExecutor = Executors.newSingleThreadExecutor();
    }

    @Scheduled(initialDelay = 0, fixedRate = 3_600_000)
    public long calculatePrices() {

        if (isRunning.compareAndSet(false, true)) {

            LOG.info("Trigger price calculation job.");
            jobExecutor.execute(this::calculate);
            return System.currentTimeMillis();

        } else {
            LOG.info("isRunning true, Another job is running");
            return -1;
        }
    }

    void calculate() {
        List<ProductVO> products = productService.findAllProducts();
        LOG.info("Product entries: {}", products.size());

        for (ProductVO product : products) {
            PriceCalculationTask priceCalculationTask = (PriceCalculationTask)
                    applicationContext.getBean("PriceCalculationTask", product.getId());

            LOG.info("Submitting calculation job for product {}", product.getId());
            taskExecutor.execute(priceCalculationTask);
        }
        isRunning.set(false);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ProductService productService;

    @Value("${pricing.calculationJob.threads:4}")
    private int threadCount;
}
