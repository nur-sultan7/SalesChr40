package com.akhmadov.saleschr2018;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.akhmadov.saleschr2018.data.FollowingShop;
import com.akhmadov.saleschr2018.data.MainModelView;
import com.squareup.picasso.Picasso;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ShopAbout extends AppCompatActivity {

    String shop_tel_number;
    String shop_inst_link;
    String shop_location_str;
    boolean inst = true;
    boolean tel = true;
    MainModelView mainModelView;
  private TextView shopFollow;
  private boolean isFollowingShop;
  private String shopId;
  private String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_about);
        mainModelView= ViewModelProviders.of(this).get(MainModelView.class);
        TextView shop_name = findViewById(R.id.shop_name);
        TextView shop_description = findViewById(R.id.shop_description);
        TextView shop_location_name = findViewById(R.id.shop_location_name);
        shopFollow =findViewById(R.id.shop_follow);
        LinearLayout shop_location = findViewById(R.id.shop_location);
        ImageView shop_img = findViewById(R.id.shop_image);
        ImageView shop_phone = findViewById(R.id.shop_phone);
        ImageView shop_inst = findViewById(R.id.shop_inst);
        ImageView shop_whatsapp = findViewById(R.id.shop_whatsapp);
        LinearLayout linearLayout = findViewById(R.id.shop_about);
        shopId =getIntent().getStringExtra("shop_id");
        final String shop_big_img = getIntent().getStringExtra("shop_img");
        Picasso.get().load(shop_big_img).into(shop_img);
        shop_tel_number = getIntent().getStringExtra("shop_tel");
        String description = getIntent().getStringExtra("shop_description");
        if (description != null) {
            shop_description.setText(description.equals("null") ? "Описание отсутствует" : description);
        }
        shopName=getIntent().getStringExtra("shop_name");
        shop_name.setText(shopName);
        shop_location_str = getIntent().getStringExtra("shop_location");
        if (shop_location_str == null || "null".equals(shop_location_str) || "".equals(shop_location_str))
            shop_location.setVisibility(View.GONE);
        else
            shop_location_name.setText(shop_location_str);
        shop_inst_link = getIntent().getStringExtra("shop_inst");

        if ("null".equals(shop_inst_link) || shop_inst_link == null) {
            shop_inst.setVisibility(View.GONE);
            inst = false;
        }
        if ("null".equals(shop_tel_number) || shop_tel_number == null) {
            shop_phone.setVisibility(View.GONE);
            shop_whatsapp.setVisibility(View.GONE);
            tel = false;
        }
        if (!tel && !inst) {
            linearLayout.setVisibility(View.GONE);
        }

        shop_inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Instagram(shop_inst_link);
            }
        });
        shop_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Open_Phone(shop_tel_number);
            }
        });
        shop_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Whatsapp(shop_tel_number);
            }
        });
        shop_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + shop_location_str);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        isFollowingShop=mainModelView.getFollowingShopById(shopId)!=null;
        checkIsFollowingShop(isFollowingShop);
        shopFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFollowingShop)
                {
                    isFollowingShop=false;
                    mainModelView.deleteFollowingShop(shopId);
                }
                else
                {
                    isFollowingShop=true;
                    mainModelView.insertFollowingShop(new FollowingShop(shopId,shopName,shop_big_img));
                }
                checkIsFollowingShop(isFollowingShop);
            }
        });


    }

    private void Open_Instagram(String inst) {
        Intent intentAiguilleur;
        String scheme = "http://instagram.com/_u/" + inst;
        String path = "https://instagram.com/" + inst;
        String nomPackageInfo = "com.instagram.android";
        try {
            getPackageManager().getPackageInfo(nomPackageInfo, 0);
            intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
        } catch (Exception e) {
            intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        }
        startActivity(intentAiguilleur);
    }

    private void Open_Phone(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void Open_Whatsapp(String number_phone) {
        try {
            String number = number_phone.replace(" ", "").replace("+", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
            startActivity(sendIntent);

        } catch (Exception e) {
            Log.e(TAG, "ERROR_OPEN_MESSANGER" + e.toString());
        }
    }
    public void checkIsFollowingShop(boolean isFollowingShop)
    {
        if (isFollowingShop) {
            shopFollow.setText("Отписаться");
            shopFollow.setBackground(getResources().getDrawable(R.drawable.rouded_red_background_textview));

        }
        else {
            shopFollow.setText("Подписаться");
            shopFollow.setBackground(getResources().getDrawable(R.drawable.rouded_blue_background_textview));
        }
    }

}
