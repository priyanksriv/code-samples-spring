package com.prizy.product.domain.entity;

import com.prizy.common.entity.BaseEntity;

import javax.persistence.Entity;


@Entity
public class PricingDetails extends BaseEntity {

    private String product;
    private Double averagePrice;
    private Double lowestPrice;
    private Double highestPrice;
    private Double idealPrice;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Double getIdealPrice() {
        return idealPrice;
    }

    public void setIdealPrice(Double idealPrice) {
        this.idealPrice = idealPrice;
    }

    @Override
    public String toString() {
        return "PricingDetails{" +
                "product='" + product + '\'' +
                ", averagePrice=" + averagePrice +
                ", lowestPrice=" + lowestPrice +
                ", highestPrice=" + highestPrice +
                ", idealPrice=" + idealPrice +
                ", created='" + getCreated() + '\'' +
                '}';
    }
}
