package com.akhmadov.saleschr2018.fragments;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Created by Akhmadov on 02.10.2017.
 */

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> implements Filterable {

    // Declare Variables
    private Context context;
    private List<Shop> Shopslist ;
    Filter filter;
    private  static  int position_about=-1;

    SalesAdapter(Context context,
                       List<Shop> Shopslist) {
        this.context = context;
        this.Shopslist = Shopslist;
    }

    @Override
    public Filter getFilter() {

        if(filter == null)
            filter = new CheeseFilter();
        return filter;
    }

    public class CheeseFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            constraint = constraint.toString().toLowerCase();

            FilterResults newFilterResults = new FilterResults();

            if (constraint.length() > 0) {


                ArrayList<Shop> auxData = new ArrayList<>();

                for (int i = 0; i < Shopslist.size(); i++) {
                    if (Shopslist.get(i).getName().toLowerCase().contains(constraint))
                        auxData.add(Shopslist.get(i));
                }

                newFilterResults.count = auxData.size();
                newFilterResults.values = auxData;
            } else {

                newFilterResults.count = Shopslist.size();
                newFilterResults.values = Shopslist;
            }

            return newFilterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayList<Shop> resultData = new ArrayList<>();

            resultData = (ArrayList<Shop>) results.values;

            //QuestionListViewAdapter adapter = new QuestionListViewAdapter(context, resultData);
            Shopslist = resultData;

            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public SalesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.sales_shops_item_restyle,parent,false);
        return new SalesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Shop shop = Shopslist.get(position);
        holder.shop_name.setText(shop.getName());
        holder.location.setText(shop.getLocation());
        if (shop.getDescription()!=null) {
            holder.description.setText(shop.getDescription().equals("null")?"Отсутствует описание":shop.getDescription());
        }
        String image_st = shop.getImage();
        Picasso.get()
                .load(shop.getBig_image())
                .into(holder.Image);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int deviceh = (int) (displaymetrics.heightPixels / 3.15);
        holder.Image.getLayoutParams().height = deviceh;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AppCompatActivity activity = (AppCompatActivity) arg0.getContext();
                Fragment tovarsFrag = new TovarsRecyclerFrag();
                Bundle bundle = new Bundle();
                bundle.putString("shop", shop.getName());
                bundle.putString("shop_id", shop.getShop_id());
                bundle.putString("shop_img",shop.getBig_image());
                bundle.putString("shop_location",shop.getLocation());
                bundle.putString("shop_tel", shop.getTel());
                bundle.putString("shop_description",shop.getDescription());
                bundle.putString("shop_inst",shop.getInst());
               // bundle.putString("shop",shop.);
                tovarsFrag.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, tovarsFrag).addToBackStack(null).commit();
            }
        });
        ((ViewGroup)holder.root_anim).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);


        if (shop.isPosition_about())
        {
            holder.shop_about.setVisibility(View.VISIBLE);
            holder.animation.stop();
            ((AnimationDrawable)( holder.shop_about_btn_image.getBackground())).stop();
            holder.shop_about_btn_image.setBackgroundDrawable(null);
            holder.shop_about_btn_image.setBackgroundResource(R.drawable.anim_shops_about_revers);
            holder.description.setMaxLines(100);
            holder.description.setEllipsize(null);


        }
        else {
            holder.shop_about.setVisibility(View.GONE);
            ((AnimationDrawable)( holder.shop_about_btn_image.getBackground())).stop();
            holder.shop_about_btn_image.setBackgroundDrawable(null);
            holder.shop_about_btn_image.setBackgroundResource(R.drawable.anim_shops_about);
            holder.description.setMaxLines(4);
            holder.description.setEllipsize(TextUtils.TruncateAt.END);


        }
        holder.animation= (AnimationDrawable) holder.shop_about_btn_image.getBackground();
        holder.animation.setOneShot(true);

        holder.shop_about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.shop_about.getVisibility()==View.VISIBLE)
                {
                    holder.animation.stop();
                    holder.animation.start();
                    holder.shop_about.setVisibility(View.GONE);
                    holder.description.setMaxLines(4);
                    holder.description.setEllipsize(TextUtils.TruncateAt.END);
                    Shopslist.get(position).setPosition_about(false);

                }
                else {
                    holder.animation.stop();
                    holder.animation.start();
                    holder.shop_about.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,                 // fromXDelta
                            0,                 // toXDelta
                            -holder.shop_about.getHeight()/2,  // fromYDelta
                            0);                // toYDelta
                    animate.setDuration(300);
                    holder.shop_about.startAnimation(animate);
                    Shopslist.get(position).setPosition_about(true);
                    ((AnimationDrawable)( holder.shop_about_btn_image.getBackground())).stop();
                    holder.shop_about_btn_image.setBackgroundDrawable(null);
                    holder.shop_about_btn_image.setBackgroundResource(R.drawable.anim_shops_about_revers);
                    holder.animation= (AnimationDrawable) holder.shop_about_btn_image.getBackground();
                    holder.animation.setOneShot(true);
                    holder.description.setMaxLines(100);
                    holder.description.setEllipsize(null);
                }
            }
        });

        String shop_inst_link = shop.getInst();
        String shop_tel_number = shop.getTel();
        if ("null".equals(shop_inst_link) || shop_inst_link==null || "".equals(shop_inst_link)) {
            holder.inst.setVisibility(View.GONE);
        }
        else
        {
            holder.inst.setVisibility(View.VISIBLE);
        }
        if ("null".equals(shop_tel_number) || shop_tel_number==null || "".equals(shop_tel_number)) {
            holder.phone_number.setVisibility(View.GONE);
            holder.whatsapp.setVisibility(View.GONE);
        }
        else
        {
            holder.phone_number.setVisibility(View.VISIBLE);
            holder.whatsapp.setVisibility(View.VISIBLE);
        }

        holder.inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAiguilleur;
                String scheme = "http://instagram.com/_u/"+Shopslist.get(position).getInst();
                String path = "https://instagram.com/"+Shopslist.get(position).getInst();
                String nomPackageInfo ="com.instagram.android";
                try {
                    context.getPackageManager().getPackageInfo(nomPackageInfo, 0);
                    intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
                } catch (Exception e) {
                    intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                }
                context.startActivity(intentAiguilleur);
            }
        });
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String number = shop.getTel().replace(" ", "").replace("+", "");

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
                    context.startActivity(sendIntent);

                } catch(Exception e) {
                    Log.e(TAG, "ERROR_OPEN_MESSANGER"+e.toString());
                }
            }
        });
        holder.phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + shop.getTel()));
                context.startActivity(intent);
            }
        });
        if (shop.getLocation()==null || "null".equals(shop.getLocation()) ||  "".equals(shop.getLocation()))
            holder.shop_location_linearLayout.setVisibility(View.GONE);
        else
            holder.shop_location_linearLayout.setVisibility(View.VISIBLE);
        holder.shop_location_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+shop.getLocation());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return Shopslist.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView shop_name;
        TextView location;
        TextView description;
        ImageView Image;
        ImageView shop_about_btn_image;
        AnimationDrawable animation;
        LinearLayout shop_about_btn;
        LinearLayout shop_about;
        LinearLayout root_anim;
        ImageView inst;
        ImageView whatsapp;
        ImageView phone_number;

        LinearLayout shop_location_linearLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.shop_image);
            location = itemView.findViewById(R.id.shop_location_name);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_about_btn=itemView.findViewById(R.id.shop_about_btn);
            shop_about=itemView.findViewById(R.id.shop_about);
            shop_about_btn_image = (ImageView) itemView.findViewById(R.id.shop_about_btn_image);
            root_anim = itemView.findViewById(R.id.root_amin);
            description = itemView.findViewById(R.id.shop_description);
            inst=itemView.findViewById(R.id.shop_inst);
            whatsapp=itemView.findViewById(R.id.shop_whatsapp);
            phone_number=itemView.findViewById(R.id.shop_phone);

            shop_location_linearLayout=itemView.findViewById(R.id.shop_location);
        }
    }


}

