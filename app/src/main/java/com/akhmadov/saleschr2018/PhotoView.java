package com.akhmadov.saleschr2018;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Akhmadov on 15.02.2017.
 */

public class PhotoView extends AppCompatActivity {
    ViewPager viewPager;
    PhotoViewViewPageAdapter adapter;
    List<String> images;
    TextView pages_nomber;
   static String image_drawable;
    static String big_image_drawable;
    ImageView imageClose;
//   ImageLoader_image_message imageLoader =new ImageLoader_image_message(this);

    PhotoViewAttacher mAttacher;

    static Context this_class;
    ImageLoader imageLoader = new ImageLoader(this);

    public static void setImage_drawable(String image_draweble, String big_image_drawable) {
        PhotoView.image_drawable = image_draweble;
        PhotoView.big_image_drawable=big_image_drawable;
    }public static void setImage_drawable( String big_image_drawable) {

        PhotoView.big_image_drawable=big_image_drawable;
    }


    //ImageView image_view;
    uk.co.senab.photoview.PhotoView image_view2;

    PhotoViewAttacher photo_view;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview_viewpager);
        this_class=this;
        pages_nomber = findViewById(R.id.photoView_viewpage_pages_nomber);
        imageClose = findViewById(R.id.photoView_viewpage_close);

        Intent intent = getIntent();
        images = new ArrayList<>();
        if (intent.getExtras().getString("image1")!=null)
            images.add(intent.getExtras().getString("image1"));
        if (intent.getExtras().getString("image2")!=null)
            images.add(intent.getExtras().getString("image2"));
        if (intent.getExtras().getString("image3")!=null)
            images.add(intent.getExtras().getString("image3"));
        adapter = new PhotoViewViewPageAdapter(images,this);
        viewPager = findViewById(R.id.photoView_viewpage);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0,0,0,0);
        viewPager.setCurrentItem(intent.getExtras().getInt("position"));
        String nomber = intent.getExtras().getInt("position")+1+" фото из "+images.size();
        pages_nomber.setText(nomber);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position<(adapter.getCount()-1)){

                }
            }

            @Override
            public void onPageSelected(int position) {
               // toggleArrowVisibility(position == 0, position == images.size() - 1);
                String nomber = (position+1)+" фото из "+images.size();
                pages_nomber.setText(nomber);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }

    // TODO: Rename method, update argument and hook method into UI event
    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {


           // mAttacher = new PhotoViewAttacher(image_view);
        }
    }
}
