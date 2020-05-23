package com.akhmadov.saleschr2018.data;

/**
 * Created by Akhmadov on 02.10.2017.
 */

public class Shop {
    private String name;
    private String location;
    private String image;
    private String shop_id;
    private String big_image;
    private String tel;
    private String description;
    private String inst;
    private boolean position_about=false;

    public boolean isPosition_about() {
        return position_about;
    }

    public void setPosition_about(boolean position_about) {
        this.position_about = position_about;
    }

    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBig_image() {
        return big_image;
    }

    public void setBig_image(String big_image) {
        this.big_image = big_image;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public Shop() {
    }

    public Shop(String shop_id, String name, String location, String image ) {
        this.name = name;
        this.location = location;

        this.shop_id = shop_id;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
