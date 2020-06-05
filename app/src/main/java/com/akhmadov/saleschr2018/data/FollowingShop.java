package com.akhmadov.saleschr2018.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "following_shops")
public class FollowingShop extends Shop {
    @PrimaryKey
    private int unique_id;
    public FollowingShop(int unique_id,String name, String location, String image, String shop_id, String big_image, String tel, String description, String inst) {
        super(name, location, image, shop_id, big_image, tel, description, inst);
        this.unique_id=unique_id;
    }

    public int getUnique_id() {
        return unique_id;
    }

    @Ignore
    public FollowingShop(Shop shop)
    {
        super(shop.getName(),shop.getLocation(),shop.getImage(),shop.getShop_id(),shop.getBig_image(),shop.getTel(),shop.getDescription(),shop.getInst());
    }
}
