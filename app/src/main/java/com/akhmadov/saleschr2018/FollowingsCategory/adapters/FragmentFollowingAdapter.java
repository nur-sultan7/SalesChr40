package com.akhmadov.saleschr2018.FollowingsCategory.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Tovar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FragmentFollowingAdapter extends RecyclerView.Adapter<FragmentFollowingAdapter.ViewHolder> {
    private List<Tovar> tovarList;

    public FragmentFollowingAdapter() {
        tovarList=new ArrayList<>();
    }

    public List<Tovar> getTovarList() {
        return tovarList;
    }

    public void setTovarList(List<Tovar> tovarList) {
        this.tovarList = tovarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_followings_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tovar tovar = tovarList.get(position);
        holder.textViewShopName.setText(tovar.getShop_name());
        Picasso.get().load(tovar.getShop_img())
                .into(holder.imageViewShop);
        Picasso.get().load(tovar.getImage())
                .into(holder.imageViewTovarImage);
        holder.textViewTovarName.setText(tovar.getName());
        holder.textViewTovarCategory.setText(tovar.getCategory());
        holder.textViewTovarNewPrice.setText(String.valueOf(tovar.getNew_cena()));
        holder.textViewTovarOldPrice.setText(String.valueOf(tovar.getOld_cena()));
        holder.textViewTovarOldPrice.setPaintFlags(holder.textViewTovarOldPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.textViewTovarSkidka.setText(String.valueOf(tovar.getSkidka())+"%");
    }

    @Override
    public int getItemCount() {
        return tovarList.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder
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
            textViewTovarName=itemView.findViewById(R.id.FollowingTovarName);
            textViewTovarCategory= itemView.findViewById(R.id.FollowingTovarCategory);
            textViewTovarNewPrice=itemView.findViewById(R.id.FollowingTovarNewPrice);
            textViewTovarOldPrice=itemView.findViewById(R.id.FollowingTovarOldPrice);
            textViewTovarSkidka=itemView.findViewById(R.id.FollowingTovarSkidka);

        }
    }
}
