package com.prizy.product.mapper;

import com.prizy.product.domain.entity.Product;
import com.prizy.product.vo.ProductVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductVOToProductMapper {

    public Product map(ProductVO vo) {
        Product entity = new Product();
        entity.setId(vo.getId());
        entity.setProductName(vo.getProductName());
        entity.setDescription(vo.getDescription());
        entity.setBasePrice(vo.getBasePrice());
        entity.setCreated(vo.getCreated());
        return entity;
    }

    public List<Product> mapList(Iterable<ProductVO> vos) {
        List<Product> entities = new ArrayList<>();
        for (ProductVO vo : vos) {
            entities.add(map(vo));
        }
        return entities;
    }
}
