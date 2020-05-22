package com.akhmadov.saleschr2018;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoViewViewPageAdapter extends PagerAdapter {
    private List<String> images;
    private LayoutInflater layoutInflater;
    private Context context;


    public PhotoViewViewPageAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_photoview,container,false);
         ImageView imageView = view.findViewById(R.id.photoView2);
        final ProgressBar progressBar;  progressBar =  view.findViewById(R.id.photoView_progressBar) ;
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(images.get(position))
                .into(imageView, new Callback() {

                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);

                            }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                    }


                        });
        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
}
