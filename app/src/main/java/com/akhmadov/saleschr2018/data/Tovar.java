package com.akhmadov.saleschr2018.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.parse.ParseObject;



/**
 * Created by Akhmadov on 15.10.2017.
 */
@Entity(tableName = "tovars")
public class Tovar {
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private String id_tovar;
    private String name;
    private String opisanie;
    private String image;
    private String big_image;
    private String big_image2;
    private String big_image3;
    private int skidka;
    private int new_cena;
    private String old_cena;
    private String created_at;
    private boolean is_fav = false;
    private String shop_name;
    private String shop_id;

    public int getUniqueId() {
        return uniqueId;
    }

    public boolean isIs_fav() {
        return is_fav;
    }

    public void setIs_fav(boolean is_fav) {
        this.is_fav = is_fav;
    }

@Ignore
    public Tovar() {

    }

    public Tovar(int uniqueId, String id_tovar, String name, String opisanie, String image, String big_image, String big_image2,
                 String big_image3, int skidka, int new_cena, String old_cena, String created_at, boolean is_fav, String shop_name,
                 String shop_id, String first_name, long time, String category, String description) {
        this.uniqueId = uniqueId;
        this.id_tovar = id_tovar;
        this.name = name;
        this.opisanie = opisanie;
        this.image = image;
        this.big_image = big_image;
        this.big_image2 = big_image2;
        this.big_image3 = big_image3;
        this.skidka = skidka;
        this.new_cena = new_cena;
        this.old_cena = old_cena;
        this.created_at = created_at;
        this.is_fav = is_fav;
        this.shop_name = shop_name;
        this.shop_id = shop_id;
        this.first_name = first_name;
        this.time = time;
        this.category = category;
        this.description = description;
    }

    @Ignore
    public Tovar(String id_tovar, String name, String opisanie, String image, String big_image, String big_image2, String big_image3, int skidka,
                 int new_cena, String old_cena, String created_at, boolean is_fav, String shop_name, String shop_id, ParseObject shop_object, String first_name, long time, String category, String description) {
        this.id_tovar = id_tovar;
        this.name = name;
        this.opisanie = opisanie;
        this.image = image;
        this.big_image = big_image;
        this.big_image2 = big_image2;
        this.big_image3 = big_image3;
        this.skidka = skidka;
        this.new_cena = new_cena;
        this.old_cena = old_cena;
        this.created_at = created_at;
        this.is_fav = is_fav;
        this.shop_name = shop_name;
        this.shop_id = shop_id;
        this.shop_object = shop_object;
        this.first_name = first_name;
        this.time = time;
        this.category = category;
        this.description = description;
    }

    @Ignore
    public ParseObject getShop_object() {
        return shop_object;
    }

    @Ignore
    public void setShop_object(ParseObject shop_object) {
        this.shop_object = shop_object;
    }

    @Ignore
    private ParseObject shop_object;
    private String first_name;
    private long time;
    private String category;
    private String description;

    public String getBig_image2() {
        return big_image2;
    }

    public void setBig_image2(String big_image2) {
        this.big_image2 = big_image2;
    }

    public String getBig_image3() {
        return big_image3;
    }

    public void setBig_image3(String big_image3) {
        this.big_image3 = big_image3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getId_tovar() {
        return id_tovar;
    }

    public void setId_tovar(String id_tovar) {
        this.id_tovar = id_tovar;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_name() {
        return shop_name;
    }



    public int getNew_cena() {
        return new_cena;
    }

    public void setNew_cena(int new_cena) {
        this.new_cena = new_cena;
    }

    public String getOld_cena() {
        return old_cena;
    }

    public void setOld_cena(String old_cena) {
        this.old_cena = old_cena;
    }

    public void setSkidka(int skidka) {
        this.skidka = skidka;
    }

    public int getSkidka() {
        return skidka;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOpisanie() {
        return opisanie;
    }

    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBig_image() {
        return big_image;
    }

    public void setBig_image(String big_image) {
        this.big_image = big_image;
    }
}
