package com.prizy.product.service;

import java.util.List;

public interface PricingService {

    default Double compute(List<Double> priceList, boolean sortedAscending) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    Double compute(List<Double> priceList);
}
