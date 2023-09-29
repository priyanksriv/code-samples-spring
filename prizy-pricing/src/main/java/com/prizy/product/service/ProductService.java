package com.prizy.product.service;

import com.prizy.product.vo.PricingDetailsVO;
import com.prizy.product.vo.ProductVO;

import java.util.List;

public interface ProductService {

    List<ProductVO> findAllProducts();

    ProductVO createProduct(ProductVO productVO);

    ProductVO findProduct(String productId);

    ProductVO updateProduct(String productId, ProductVO productVO);

    ProductVO deleteProduct(String productId);

    PricingDetailsVO getPrices(String productId);
}
