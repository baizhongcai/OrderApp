package com.bzc.orderapp.bean;

import java.io.Serializable;

public class FoodInfoBean implements Serializable {

    private String name;
    private int monery;
    private int imgId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FoodInfoBean(String name, int monery, int imgId, String id){
        this.name = name;
         this.monery = monery;
         this.imgId = imgId;
         this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonery() {
        return monery;
    }

    public void setMonery(int monery) {
        this.monery = monery;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
