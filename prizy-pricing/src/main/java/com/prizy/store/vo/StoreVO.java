package com.prizy.store.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class StoreVO {

    private String id;
    private String storeName;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private Date created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "StoreVO{" +
                "storeName='" + storeName + '\'' +
                ", created=" + created +
                '}';
    }
}
