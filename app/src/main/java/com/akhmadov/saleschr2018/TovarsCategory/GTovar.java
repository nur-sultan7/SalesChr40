package com.akhmadov.saleschr2018.TovarsCategory;

import com.google.gson.annotations.SerializedName;
import com.parse.ParseFile;

import java.security.acl.Acl;
import java.util.Date;
import java.util.List;

public class GTovar  {
    @SerializedName("objectId")
    public String objectId;
    @SerializedName("tovar_name")
    public String tovar_name;
    public String tovar_opisanie;
    public String shop_id;
    public List<String> cats_id;
    public ParseFile tovar_image;
    public ParseFile tovar_big_image2;
    public String tovar_description;
    public ParseFile tovar_big_image;
    public Acl ACL;
    public ParseFile tovar_big_image3;
    public Date updatedAt;
    public String shop_name;
    public Date createdAt ;
    public String tovar_skidka;
    public String tovar_old_price;

}
