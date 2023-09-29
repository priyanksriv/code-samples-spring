package com.prizy.product.domain.entity;

import com.prizy.common.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Product extends BaseEntity {

    private String productName;
    private String description;
    private Double basePrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", basePrice=" + basePrice +
                ", created='" + getCreated() + '\'' +
                '}';
    }
}
