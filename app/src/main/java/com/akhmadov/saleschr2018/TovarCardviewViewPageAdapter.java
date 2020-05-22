package com.akhmadov.saleschr2018;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TovarCardviewViewPageAdapter  extends PagerAdapter {
    private List<String> images;
    private LayoutInflater layoutInflater;
    private Context context;
    private String tovar_image;

    private DisplayMetrics displaymetrics;
    private final int deviceh;
    private final int devicew;


    public TovarCardviewViewPageAdapter(List<String> images,String tovar_image, Context context) {
        this.images = images;
        this.context = context;
        this.tovar_image =tovar_image;

         displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
         deviceh = (int) (displaymetrics.heightPixels / 2.25);
         devicew =  (displaymetrics.widthPixels);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @SuppressLint("CheckResult")
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_tovar_cardview_images,container,false);
        final ImageView imageView = view.findViewById(R.id.tovar_cardview_image);

        if (position==0)
        {
            Picasso.get()
                    .load(tovar_image)
                    .noPlaceholder()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .resize(devicew, deviceh)
                    .centerCrop()
                    .into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                    Picasso.get()
                            .load(images.get(position))
                            .noPlaceholder()
                            .resize(devicew, deviceh)
                            .centerCrop()
                            .into(imageView);
            }

                        @Override
                        public void onError(Exception e) {

                        }

        });


        }
        else {

            Picasso.get()
                    .load(images.get(position))
                    .into(imageView);
        }
        container.addView(view, 0);
       imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PhotoView.setImage_drawable(images.get(position));
                Intent intent = new Intent(context, PhotoView.class);
                if (images.size()>=1)
                intent.putExtra("image1",images.get(0));
                if (images.size()>=2)
                intent.putExtra("image2",images.get(1));
                if (images.size()==3)
                intent.putExtra("image3",images.get(2));
                intent.putExtra("position",position);
                intent.putExtra("size",images.size());


                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
}
