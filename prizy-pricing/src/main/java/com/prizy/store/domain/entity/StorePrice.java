package com.prizy.store.domain.entity;

import com.prizy.common.entity.BaseEntity;
import com.prizy.product.domain.entity.Product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class StorePrice extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double price;
    private String notes;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "StorePrice{" +
                store == null ? "" : "store='" + store.getStoreName() + '\'' +
                product == null ? "" :", product='" + product.getProductName() + '\'' +
                ", price=" + price +
                ", created='" + getCreated() + '\'' +
                '}';
    }
}
