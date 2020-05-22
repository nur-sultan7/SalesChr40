package com.akhmadov.saleschr2018.fragments;

public class ShopCategory {
    private String name;
    private Integer DrawebleRes;
    private  int id;

    public ShopCategory(String name, Integer drawebleRes, int id) {
        this.name = name;
        DrawebleRes = drawebleRes;
        this.id = id;
    }

    public ShopCategory(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDrawebleRes() {
        return DrawebleRes;
    }

    public void setDrawebleRes(Integer drawebleRes) {
        DrawebleRes = drawebleRes;
    }
}
