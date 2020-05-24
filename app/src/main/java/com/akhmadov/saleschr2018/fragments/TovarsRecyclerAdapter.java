package com.akhmadov.saleschr2018.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.akhmadov.saleschr2018.ImageLoader;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.TovarCardview;
import com.akhmadov.saleschr2018.data.Tovar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;
/////////NEVER USW/////////////
public class TovarsRecyclerAdapter extends RecyclerView.Adapter<TovarsRecyclerAdapter.MyViewHolder> {


    private Context mContext;
    private List<Tovar> mData;
    Filter filter;
    private DisplayMetrics displaymetrics;
    private final int deviceh;
    private final int devicew;
    private OnLikeClickListener onLikeClickListener;
    private OnUnLikeClickListener onUnLikeClickListener;


    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    public void setOnUnLikeClickListener(OnUnLikeClickListener onUnLikeClickListener) {
        this.onUnLikeClickListener = onUnLikeClickListener;
    }

    interface OnLikeClickListener{
        void onClick(int position);
    }
    interface OnUnLikeClickListener
    {
        void onClick(int position);
    }

 public TovarsRecyclerAdapter(Context mContext, List<Tovar> mData) {
        this.mContext = mContext;
        this.mData = mData;
      displaymetrics = new DisplayMetrics();
      ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
      deviceh = (int) (displaymetrics.heightPixels / 2.25);
      devicew =  (displaymetrics.widthPixels);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view =  mInflater.inflate(R.layout.all_tovars_recycler_card_item,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ImageLoader imageLoader = new ImageLoader(mContext);
        final Tovar tovar = mData.get(position);
        holder.tovar_name.setText(tovar.getName());
        Picasso.get()
                .load(tovar.getImage())
                .resize(devicew, deviceh)
                .centerCrop()
                .into(holder.tovar_image);
        holder.old_cena.setText(tovar.getOld_cena());
        holder.new_cena.setText(tovar.getNew_cena());
        holder.tovar_category.setText(tovar.getCategory());
        holder.skidka.setText(tovar.getSkidka()+"%");
        holder.old_cena.setPaintFlags(holder.old_cena.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = (int) (displaymetrics.widthPixels / 2.15);

        //if you need 4-5-6 anything fix imageview in height
        //int deviceheight = displaymetrics.heightPixels / 3-10;

        holder.tovar_image.getLayoutParams().width = devicewidth;
        holder.cardView.getLayoutParams().width = devicewidth;

        //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
     //   holder.tovar_image.getLayoutParams().height = deviceheight;
        if (tovar.isIs_fav())






        //imageLoader.DisplayImage(mData.get(position).getImage(), holder.tovar_image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, TovarCardview.class);
                intent.putExtra("tovar_name", tovar.getName());
                intent.putExtra("old_cena", tovar.getOld_cena());
                intent.putExtra("new_cena", tovar.getNew_cena());
                intent.putExtra("skidka", tovar.getSkidka());
                intent.putExtra("tovar_category", tovar.getCategory());
                intent.putExtra("tovar_id", tovar.getId_tovar());
                intent.putExtra("tovar_image2", holder.tovar_image.getDrawable().toString());
                intent.putExtra("tovar_image", tovar.getImage());
                intent.putExtra("tovar_description",tovar.getDescription());
                intent.putExtra("id",tovar.getId_tovar());
                intent.putExtra("image1",tovar.getBig_image());
                intent.putExtra("image2",tovar.getBig_image2());
                intent.putExtra("image3",tovar.getBig_image3());
                intent.putExtra("shop_id",tovar.getShop_id());
                intent.putExtra("shop_name", tovar.getShop_name());
              //  intent.putExtra("shop_img",tovar.ge)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTransitionName("selected_tovar_image");
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,holder.tovar_image,view.getTransitionName());

                    mContext.startActivity(intent,optionsCompat.toBundle());
                }
                else {
                    mContext.startActivity(intent);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tovar_name;
        ImageView tovar_image;
        CardView cardView;
        TextView tovar_category;
        TextView new_cena;
        TextView old_cena;
        LikeButton likeButton;
        TextView skidka;


     public   MyViewHolder(View itemView) {
            super(itemView);
            tovar_name = itemView.findViewById(R.id.tovar_r_name);
            tovar_image = itemView.findViewById(R.id.tovar_r_image);
            cardView = itemView.findViewById(R.id.cardview_id);
            likeButton = itemView.findViewById(R.id.tovar_r_like2);
            likeButton.setUnlikeDrawableRes(R.drawable.heart);
            likeButton.setLikeDrawableRes(R.drawable.heart_2);
            tovar_category = itemView.findViewById(R.id.tovar_r_category);
            new_cena = itemView.findViewById(R.id.textViewFavouriteTovarNewCena);
            old_cena = itemView.findViewById(R.id.textViewFavouriteTovarOldCena);
            skidka = itemView.findViewById(R.id.tovar_r_skidka);
            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (onLikeClickListener!=null)
                    {
                        onLikeClickListener.onClick(getAdapterPosition());
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (onUnLikeClickListener!=null)
                    {
                        onUnLikeClickListener.onClick(getAdapterPosition());
                    }

                }
            });

        }
    }
}
