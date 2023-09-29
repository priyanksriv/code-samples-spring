package com.prizy.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ProductVO {

    private String id;
    private String productName;
    private String description;
    private Double basePrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private Date created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "productName='" + productName + '\'' +
                ", basePrice=" + basePrice +
                ", created=" + created +
                '}';
    }
}
