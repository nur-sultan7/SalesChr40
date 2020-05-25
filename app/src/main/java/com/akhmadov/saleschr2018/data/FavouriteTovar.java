package com.akhmadov.saleschr2018.data;

import androidx.room.Entity;

@Entity(tableName = "favourite_tovars")
public class FavouriteTovar extends Tovar {

    public FavouriteTovar(int uniqueId, String id_tovar, String name, String opisanie, String image, String big_image, String big_image2, String big_image3, int skidka, int new_cena, String old_cena, String created_at, boolean is_fav, String shop_name, String shop_id,  String first_name, long time, String category, String description) {
        super(uniqueId, id_tovar, name, opisanie, image, big_image, big_image2, big_image3, skidka, new_cena, old_cena, created_at, is_fav, shop_name, shop_id,  first_name, time, category, description);
    }

    public FavouriteTovar(Tovar tovar) {
        super(tovar.getId_tovar(),tovar.getName(),tovar.getOpisanie(),tovar.getImage(),tovar.getBig_image(),tovar.getBig_image2(),tovar.getBig_image3(),tovar.getSkidka(),tovar.getNew_cena(),tovar.getOld_cena(),
                tovar.getCreated_at(),tovar.isIs_fav(),tovar.getShop_name(),tovar.getShop_id(),tovar.getShop_object(),tovar.getFirst_name(),tovar.getTime(),tovar.getCategory(),tovar.getDescription());
    }
}
