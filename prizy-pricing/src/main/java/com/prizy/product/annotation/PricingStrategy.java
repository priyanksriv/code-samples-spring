package com.prizy.product.annotation;

import com.prizy.product.enumeration.StrategyName;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface PricingStrategy {

    StrategyName strategyName();
}
