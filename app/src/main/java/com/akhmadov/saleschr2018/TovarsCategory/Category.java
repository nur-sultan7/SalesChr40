package com.akhmadov.saleschr2018.TovarsCategory;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private String img;
    private String id;
    private String root;
    private ArrayList<String> tabs;

    public ArrayList<String> getTabs() {
        return tabs;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = (ArrayList<String>) tabs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
