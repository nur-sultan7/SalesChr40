package com.akhmadov.saleschr2018.FollowingsCategory.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akhmadov.saleschr2018.FollowingsCategory.FragmentFollowings;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Tovar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FragmentFollowingAdapter extends RecyclerView.Adapter<FragmentFollowingAdapter.ViewHolder> {
    private List<Tovar> tovarList;
    private DisplayMetrics displayMetrics;
    private  int deviceH;
    private  int deviceW;
    private  Paint paint;
    private  OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onClick(View view,ImageView imageView, int position, String currentImage);
    }
    public Tovar getItemByPosition(int position)
    {
        return tovarList.get(position);
    }

    public void setActivityDisplayMetrics(Context context)
        {
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceH = (int) (displayMetrics.heightPixels / 1.7);
        deviceW =  (displayMetrics.widthPixels);
        paint= new Paint();
        paint.setColor(context.getResources().getColor(R.color.primary));
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
    public FragmentFollowingAdapter() {
        tovarList=new ArrayList<>();
    }

    public List<Tovar> getTovarList() {
        return tovarList;
    }

    public void setTovarList(List<Tovar> tovarList) {
        this.tovarList = tovarList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_followings_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Tovar tovar = tovarList.get(position);
        holder.textViewShopName.setText(tovar.getShop_name());
        Picasso.get().load(tovar.getShop_img())
                .into(holder.imageViewShop);
        Picasso.get().load(tovar.getImage())
                .resize(deviceW,deviceH)
                .centerCrop()
                .into(holder.imageViewTovarImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                Picasso.get().load(tovar.getBig_image())
                                        .noPlaceholder()
                                        .resize(deviceW,deviceH)
                                        .centerCrop()
                                        .into(holder.imageViewTovarImage);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                        holder.textViewTovarName.setText(tovar.getName());
        holder.textViewTovarCategory.setText(tovar.getCategory());
        holder.textViewTovarNewPrice.setText(String.valueOf(tovar.getNew_cena()));
        holder.textViewTovarOldPrice.setText(String.valueOf(tovar.getOld_cena()));

        holder.textViewTovarOldPrice.setPaintFlags(paint.getFlags());
        holder.textViewTovarSkidka.setText(String.valueOf(tovar.getSkidka())+"%");
    }

    @Override
    public int getItemCount() {
        return tovarList.size();
    }

      class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageViewShop;
        TextView textViewShopName;
        ImageView imageViewTovarImage;
        TextView textViewTovarNewPrice;
        TextView textViewTovarOldPrice;
        TextView textViewTovarSkidka;
        TextView textViewTovarName;
        TextView textViewTovarCategory;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewShop=itemView.findViewById(R.id.following_shop_image);
            textViewShopName=itemView.findViewById(R.id.following_shop_name);
            imageViewTovarImage=itemView.findViewById(R.id.FollowingTovarImage);
            imageViewTovarImage.getLayoutParams().height=deviceH;
            imageViewTovarImage.getLayoutParams().width=deviceW;
            textViewTovarName=itemView.findViewById(R.id.FollowingTovarName);
            textViewTovarCategory= itemView.findViewById(R.id.FollowingTovarCategory);
            textViewTovarNewPrice=itemView.findViewById(R.id.FollowingTovarNewPrice);
            textViewTovarOldPrice=itemView.findViewById(R.id.FollowingTovarOldPrice);
            textViewTovarSkidka=itemView.findViewById(R.id.FollowingTovarSkidka);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null)
                    {
                        onItemClickListener.onClick(view, imageViewTovarImage,getAdapterPosition(), imageViewTovarImage.getDrawable().toString());
                    }
                }
            });
        }
    }
}
