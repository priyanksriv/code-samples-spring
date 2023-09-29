package com.prizy.product.vo;

public class PricingDetailsVO {

    private String product;
    private String name;
    private String description;

    private Double basePrice;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "PricingDetailsVO{" +
                "name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", averagePrice=" + averagePrice +
                ", lowestPrice=" + lowestPrice +
                ", highestPrice=" + highestPrice +
                ", idealPrice=" + idealPrice +
                '}';
    }
}
