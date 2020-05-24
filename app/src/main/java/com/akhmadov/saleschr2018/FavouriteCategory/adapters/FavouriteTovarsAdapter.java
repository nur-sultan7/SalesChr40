package com.akhmadov.saleschr2018.FavouriteCategory.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Tovar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteTovarsAdapter extends RecyclerView.Adapter<FavouriteTovarsAdapter.ViewHolder> {
    private List<Tovar> favouriteTovarsList=new ArrayList<>();

    private OnLikeClickListener onLikeClickListener;
    private OnUnLikeClickListener onUnLikeClickListener;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFavouriteTovarsList(List<Tovar> favouriteTovarsList) {
        this.favouriteTovarsList = favouriteTovarsList;
        notifyDataSetChanged();
    }

    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    public void setOnUnLikeClickListener(OnUnLikeClickListener onUnLikeClickListener) {
        this.onUnLikeClickListener = onUnLikeClickListener;
    }
    public interface OnLikeClickListener {
        void onClick(int position);
    }

    public interface OnUnLikeClickListener {
        void onClick(int position);
    }
    public interface OnItemClickListener
    {
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.item_favourite_tovars, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tovar tovar = favouriteTovarsList.get(position);
        holder.tovar_name.setText(tovar.getName());
        Picasso.get()
                .load(tovar.getImage())
               // .resize(devicew, deviceh)
                //.centerCrop()
                .into(holder.tovar_image);
        holder.old_cena.setText(tovar.getOld_cena());
        holder.new_cena.setText(tovar.getNew_cena());
        holder.tovar_category.setText(tovar.getCategory());
        holder.skidka.setText(tovar.getSkidka() + "%");
        holder.old_cena.setPaintFlags(holder.old_cena.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (tovar.isIs_fav())
            holder.likeButton.setLiked(true);
        else
            holder.likeButton.setLiked(false);
    /*    DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int devicewidth = (int) (displaymetrics.widthPixels / 2.15);
        holder.tovar_image.getLayoutParams().width = devicewidth;
        holder.cardView.getLayoutParams().width = devicewidth;
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
                intent.putExtra("tovar_description", tovar.getDescription());
                intent.putExtra("id", tovar.getId_tovar());
                intent.putExtra("image1", tovar.getBig_image());
                intent.putExtra("image2", tovar.getBig_image2());
                intent.putExtra("image3", tovar.getBig_image3());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTransitionName("selected_tovar_image");
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, holder.tovar_image, view.getTransitionName());

                    mContext.startActivity(intent, optionsCompat.toBundle());
                } else {
                    mContext.startActivity(intent);
                }


            }
        });**/
    }

    @Override
    public int getItemCount() {
        return favouriteTovarsList.size();
    }

    public Tovar getItemByPosition(int position)
    {
        return favouriteTovarsList.get(position);
    }
    public void deleteByPosition(int position)
    {
        favouriteTovarsList.remove(position);
        notifyItemRemoved(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tovar_name;
        ImageView tovar_image;
        CardView cardView;
        TextView tovar_category;
        TextView new_cena;
        TextView old_cena;
        LikeButton likeButton;
        TextView skidka;

        ViewHolder(View itemView) {
            super(itemView);
            tovar_name = itemView.findViewById(R.id.textViewFavouriteTovarName);
            tovar_image = itemView.findViewById(R.id.imageViewFavouriteTovar);
            likeButton = itemView.findViewById(R.id.likeButtonFavouriteTovar);
            likeButton.setUnlikeDrawableRes(R.drawable.heart);
            likeButton.setLikeDrawableRes(R.drawable.heart_2);
            tovar_category = itemView.findViewById(R.id.textViewFavouriteTovarCategory);
            new_cena = itemView.findViewById(R.id.textViewFavouriteTovarNewCena);
            old_cena = itemView.findViewById(R.id.textViewFavouriteTovarOldCena);
            skidka = itemView.findViewById(R.id.textViewFavoriteTovarSkidka);
            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (onLikeClickListener != null) {
                        onLikeClickListener.onClick(getAdapterPosition());
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (onUnLikeClickListener != null) {
                        onUnLikeClickListener.onClick(getAdapterPosition());
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null)
                    {
                        onItemClickListener.onClick(getAdapterPosition());
                    }
                }
            });


        }
    }
}
