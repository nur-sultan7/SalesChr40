package com.akhmadov.saleschr2018;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.akhmadov.saleschr2018.FavouriteCategory.FavouriteTovars;
import com.akhmadov.saleschr2018.fragments.TovarsRecyclerFrag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TovarCardview extends AppCompatActivity {

    private ViewPager viewPager;
    private TovarCardviewViewPageAdapter adapter;
    private List<String> images;
    private String tovar_id;
    private ImageView left;
    private ImageView right;
    private FrameLayout frameLayout;

    private ImageView shopImage;
    private CardView shop_layout;
    private TextView shop_name;
    private TextView shopFollow;

    private int fromCategory;
    private String shop_id;
    private String txt_shop_name;
    private String txtShopImg;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.tovar_image_transition));
        }
        setContentView(R.layout.activity_tovar_cardview);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        TextView name = findViewById(R.id.cardView_tovar_name);
        TextView category = findViewById(R.id.cardView_tovar_category);
        TextView old_cena = findViewById(R.id.tovar_cardview_old_price);
        TextView new_cena = findViewById(R.id.tovar_cardview_new_price);
        TextView skidka = findViewById(R.id.tovar_cardview_skidka);
        TextView description = findViewById(R.id.cardview_tovar_description);
        shop_layout = findViewById(R.id.cardview_shop_layout);

        frameLayout = findViewById(R.id.tovar_cardview_viewpage_frame);
        left = findViewById(R.id.left_nav);
        left.setVisibility(View.INVISIBLE);
        right = findViewById(R.id.right_nav);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.arrowScroll(View.FOCUS_LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

        Intent intent = getIntent();
        fromCategory=intent.getIntExtra("from_category",0);
        name.setText(intent.getExtras().getString("tovar_name"));
        category.setText(intent.getExtras().getString("tovar_category"));
        old_cena.setText(intent.getExtras().getString("old_cena"));
        old_cena.setPaintFlags(old_cena.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        new_cena.setText(String.valueOf( intent.getExtras().getInt("new_cena")));
        String skidka_txt = intent.getExtras().getString("skidka");
        skidka.setText(intent.getExtras().getInt("skidka") + "%");
        if (intent.getExtras().getString("tovar_description") == null) {
            //description.setText("Отличный тоыар новая модель!! Есть все размеры!! Все цвета !! Ограниченная серия");
            description.setText("Отсутствует описание");
        } else {
            description.setText(intent.getExtras().getString("tovar_description"));
        }

        tovar_id = intent.getExtras().getString("id");
        images = new ArrayList<>();
        if (intent.getExtras().getString("image1") != null)
            images.add(intent.getExtras().getString("image1"));
        if (intent.getExtras().getString("image2") != null)
            images.add(intent.getExtras().getString("image2"));
        if (intent.getExtras().getString("image3") != null)
            images.add(intent.getExtras().getString("image3"));
        if (images.size() < 2)
            right.setVisibility(View.INVISIBLE);

        adapter = new TovarCardviewViewPageAdapter(images, intent.getExtras().getString("tovar_image"), this);
        viewPager = findViewById(R.id.tovar_cardview_viewpage);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0, 0, 0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int deviceheight = (int) (displaymetrics.heightPixels);

        //if you need 4-5-6 anything fix imageview in height
        //int deviceheight = displaymetrics.heightPixels / 3-10;
        viewPager.getLayoutParams().height = (int) (deviceheight / 2.25);
        frameLayout.getLayoutParams().height = (int) (deviceheight / 2.25);

        switch (fromCategory)
        {
            case 0:
                shop_layout.setVisibility(View.GONE);
                break;
            case 1:
                shop_layout.setVisibility(View.VISIBLE);
                shop_name=findViewById(R.id.cardView_shop_name);
                txt_shop_name=intent.getStringExtra("shop_name");
                shop_name.setText(txt_shop_name);
                shopImage=findViewById(R.id.cardView_shop_img);
                txtShopImg=intent.getStringExtra("shop_image");
                String shopImg= txtShopImg;
                Picasso.get().load(shopImg)
                        .into(shopImage);
                shopFollow=findViewById(R.id.cardView_shop_follow);
                shop_id=intent.getStringExtra("shop_id");
                shopFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                shop_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        AppCompatActivity activity = (AppCompatActivity) FavouriteTovars.getFavContext();
                        Fragment tovarsFrag = new TovarsRecyclerFrag();
                        Bundle bundle = new Bundle();
                        bundle.putString("shop", txt_shop_name);
                        bundle.putString("shop_id", shop_id);
                        bundle.putString("shop_img",txtShopImg);
                        tovarsFrag.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, tovarsFrag).addToBackStack(null).commitAllowingStateLoss();;
                    }
                });
                break;
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1)) {

                }
            }

            @Override
            public void onPageSelected(int position) {
                toggleArrowVisibility(position == 0, position == images.size() - 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void toggleArrowVisibility(boolean isAtZeroIndex, boolean isAtLastIndex) {
        if (isAtZeroIndex)
            left.setVisibility(View.INVISIBLE);
        else
            left.setVisibility(View.VISIBLE);
        if (isAtLastIndex)
            right.setVisibility(View.INVISIBLE);
        else
            right.setVisibility(View.VISIBLE);

    }



}
