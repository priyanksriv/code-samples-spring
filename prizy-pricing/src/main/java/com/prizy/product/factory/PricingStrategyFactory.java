package com.prizy.product.factory;

import com.prizy.product.annotation.PricingStrategy;
import com.prizy.product.enumeration.StrategyName;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Component
public class PricingStrategyFactory {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<StrategyName, Object> strategyCache = new HashMap<>();

    @PostConstruct
    public void init() {

        Map<String, Object> annotatedBeanClasses = applicationContext
                .getBeansWithAnnotation(PricingStrategy.class);

        cacheStrategies(annotatedBeanClasses.values());
    }

    private void cacheStrategies(Collection<Object> annotatedBeanClasses) {

        for (Object bean : annotatedBeanClasses) {

            PricingStrategy strategyAnnotation = AnnotationUtils.findAnnotation(
                    bean.getClass(), PricingStrategy.class);

            if (strategyAnnotation == null){
                try {
                    Object target = ((Advised) bean).getTargetSource().getTarget();
                    strategyAnnotation = AnnotationUtils.findAnnotation(
                            target.getClass(), PricingStrategy.class);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to locate annotation.");
                }
            }
            StrategyName strategyName = strategyAnnotation.strategyName();
            if (strategyCache.containsKey(strategyName)) {
                throw new RuntimeException("Found multiple strategies with name " + strategyName);
            }
            strategyCache.put(strategyName, bean);
        }
    }

    public <T> T getStrategy(StrategyName strategyName) {
        Object pricingStrategy = strategyCache.get(strategyName);
        if (pricingStrategy == null) {
            throw new RuntimeException("No strategy found with name " + strategyName);
        }
        return (T) pricingStrategy;
    }
}
