package com.akhmadov.saleschr2018.utils;

import android.content.Context;
import android.content.Intent;

import com.akhmadov.saleschr2018.TovarCardview;
import com.akhmadov.saleschr2018.data.Tovar;
/*
* fromCategory:
* 1-Favourite Cat6gory
* 0-Tovar Category
* */

public class DataUtil {
    public static Intent getIntentTovarCardView(Context mContext, int fromCategory, Tovar tovar, String currentImage)
    {
        Intent intent = new Intent(mContext, TovarCardview.class);
        intent.putExtra("from_category",fromCategory);
        intent.putExtra("tovar_name", tovar.getName());
        intent.putExtra("old_cena", tovar.getOld_cena());
        intent.putExtra("new_cena", tovar.getNew_cena());
        intent.putExtra("skidka", tovar.getSkidka());
        intent.putExtra("tovar_category", tovar.getCategory());
        intent.putExtra("tovar_id", tovar.getId_tovar());
        intent.putExtra("tovar_image2", currentImage);
        intent.putExtra("tovar_image", tovar.getImage());
        intent.putExtra("tovar_description", tovar.getDescription());
        intent.putExtra("id", tovar.getId_tovar());
        intent.putExtra("image1", tovar.getBig_image());
        intent.putExtra("image2", tovar.getBig_image2());
        intent.putExtra("image3", tovar.getBig_image3());
        intent.putExtra("shop_id",tovar.getShop_id());
        intent.putExtra("shop_name",tovar.getShop_name());
        intent.putExtra("shop_image",tovar.getShop_img());
        return intent;
    }


}
