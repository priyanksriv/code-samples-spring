package com.prizy.product.mapper;

import com.prizy.product.domain.entity.Product;
import com.prizy.product.vo.ProductVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProductToProductVOMapper {

    public ProductVO map(Product entity) {
        ProductVO vo = new ProductVO();
        vo.setId(entity.getId().toString());
        vo.setProductName(entity.getProductName());
        vo.setDescription(entity.getDescription());
        vo.setBasePrice(entity.getBasePrice());
        vo.setCreated(entity.getCreated());
        return vo;
    }

    public List<ProductVO> mapList(Iterable<Product> entities) {
        List<ProductVO> vos = new ArrayList<>();
        for (Product entity : entities) {
            vos.add(map(entity));
        }
        return vos;
    }
}
