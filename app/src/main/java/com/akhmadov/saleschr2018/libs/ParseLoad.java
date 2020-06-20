package com.akhmadov.saleschr2018.libs;

import com.akhmadov.saleschr2018.data.Tovar;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ParseLoad {

    public static List<Tovar> RetrievingTovars( List<ParseObject> ob)
    {

        List<Tovar> tovars=new ArrayList<>();
        for (final ParseObject tovar_parse : ob) {
            final Tovar tovar = new Tovar();
            ParseFile imageAsk = (ParseFile) tovar_parse.get("tovar_image");
            tovar.setImage(imageAsk.getUrl());
            ParseFile imageAsk2 = (ParseFile) tovar_parse.get("tovar_big_image");
            tovar.setBig_image(imageAsk2.getUrl());
            if (tovar_parse.get("tovar_big_image2") != null) {
                imageAsk2 = (ParseFile) tovar_parse.get("tovar_big_image2");
                tovar.setBig_image2(imageAsk2.getUrl());
            }
            if ( tovar_parse.get("tovar_big_image3")!=null)
            {
                imageAsk2 = (ParseFile) tovar_parse.get("tovar_big_image3");
                tovar.setBig_image3(imageAsk2.getUrl());
            }
            if (tovar_parse.get("tovar_description")!=null)
            {
                tovar.setDescription(tovar_parse.getString("tovar_description"));
            }
            tovar.setCategory(String.valueOf(tovar_parse.get("tovar_name")));
            tovar.setSkidka(tovar_parse.getInt("skidka"));
            tovar.setNew_cena(tovar_parse.getInt("new_price"));
            tovar.setOld_cena(tovar_parse.getString("tovar_old_price"));
            tovar.setId_tovar(tovar_parse.getObjectId());
            tovar.setName(tovar_parse.getString("tovar_brand"));
            tovar.setShop_id(tovar_parse.getString("shop_id"));
            tovar.setShop_name(tovar_parse.getString("shop_name"));
            ParseObject shop_parse = tovar_parse.getParseObject("shop_object");

                ParseFile shop_parse_image_file = shop_parse.getParseFile("shop_image");
                tovar.setShop_img(shop_parse_image_file.getUrl());

            tovar.setId_tovar(tovar_parse.getObjectId());
            tovars.add(tovar);
        }
        return tovars;
    }

}
