package com.prizy.store.domain.entity;

import com.prizy.common.entity.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Store extends BaseEntity {

    private String storeName;
    private String description;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeName='" + storeName + '\'' +
                ", created='" + getCreated() + '\'' +
                '}';
    }
}
