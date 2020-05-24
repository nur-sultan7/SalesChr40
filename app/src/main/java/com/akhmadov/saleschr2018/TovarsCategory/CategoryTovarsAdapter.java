package com.akhmadov.saleschr2018.TovarsCategory;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.TovarCardview;
import com.akhmadov.saleschr2018.data.FavouriteTovar;
import com.akhmadov.saleschr2018.data.MainModelView;
import com.akhmadov.saleschr2018.data.Tovar;
import com.akhmadov.saleschr2018.libs.ILoadMore;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryTovarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final int VIEW_LOADING=0, VIEW_ITEM=1;
    private Context mContext;
    private List<Tovar> mData =new ArrayList<>();
    private Filter filter;
    private final int deviceh;
    private final int devicew;
    private ILoadMore LoadMore;
    private boolean isloading;
    private int visibleTreshold=4;
    private int visibleLastitem, totalItemCount;

    private OnLikeClickListener onLikeClickListener;
    private OnUnLikeClickListener onUnLikeClickListener;

    private MainModelView mainModelView;


    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    public void setOnUnLikeClickListener(OnUnLikeClickListener onUnLikeClickListener) {
        this.onUnLikeClickListener = onUnLikeClickListener;
    }

    public interface OnLikeClickListener{
        void onClick(int position);
    }
    public interface OnUnLikeClickListener
    {
        void onClick(int position);
    }

 public CategoryTovarsAdapter(MainModelView mainModelView,RecyclerView recyclerView, Context mContext) {
        this.mContext = mContext;
        this.mainModelView = mainModelView;
     DisplayMetrics displaymetrics = new DisplayMetrics();
      ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
      deviceh = (int) (displaymetrics.heightPixels / 2.25);
      devicew =  (displaymetrics.widthPixels);
      final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
          @Override
          public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              totalItemCount=gridLayoutManager.getItemCount();
              visibleLastitem=gridLayoutManager.findLastVisibleItemPosition();
              if (!isloading&&totalItemCount<=visibleLastitem+4)
              {
                  if (LoadMore!=null)
                  {
                      isloading=true;
                      LoadMore.MoreLoad();
                  }
              }
          }
      });
    }

    public void setTovarsList(List<Tovar> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void  setIsloading(boolean isloading)
    {
     this.isloading=isloading;
    }

    public void setLoadMore(ILoadMore loadMore) {
        LoadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (viewType==VIEW_ITEM) {
            view = mInflater.inflate(R.layout.all_tovars_recycler_card_item, parent, false);
            return new MyViewHolder(view);
        }
        else  {
            view = mInflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder) {
            final Tovar tovar = mData.get(position);
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.tovar_name.setText(tovar.getName());
            Picasso.get()
                    .load(tovar.getImage())
                    .resize(devicew, deviceh)
                    .centerCrop()
                    .into(myViewHolder.tovar_image);
            myViewHolder.old_cena.setText(tovar.getOld_cena());
            myViewHolder.new_cena.setText(tovar.getNew_cena());
            myViewHolder.tovar_category.setText(tovar.getCategory());
            myViewHolder.skidka.setText(tovar.getSkidka() + "%");
            myViewHolder.old_cena.setPaintFlags(myViewHolder.old_cena.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            //if you need three fix imageview in width
            int devicewidth = (int) (displaymetrics.widthPixels / 2.15);
            myViewHolder.tovar_image.getLayoutParams().width = devicewidth;
            myViewHolder.cardView.getLayoutParams().width = devicewidth;
            //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
            //   holder.tovar_image.getLayoutParams().height = deviceheight;
            //imageLoader.DisplayImage(mData.get(position).getImage(), holder.tovar_image);
            FavouriteTovar favouriteTovar = mainModelView.getFavouriteTovarById(tovar.getId_tovar());
            if (favouriteTovar!=null)
                myViewHolder.likeButton.setLiked(true);
            else
                myViewHolder.likeButton.setLiked(false);
            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TovarCardview.class);
                    intent.putExtra("tovar_name", tovar.getName());
                    intent.putExtra("old_cena", tovar.getOld_cena());
                    intent.putExtra("new_cena", tovar.getNew_cena());
                    intent.putExtra("skidka", tovar.getSkidka());
                    intent.putExtra("tovar_category", tovar.getCategory());
                    intent.putExtra("tovar_id", tovar.getId_tovar());
                    intent.putExtra("tovar_image2", myViewHolder.tovar_image.getDrawable().toString());
                    intent.putExtra("tovar_image", tovar.getImage());
                    intent.putExtra("tovar_description", tovar.getDescription());
                    intent.putExtra("id", tovar.getId_tovar());
                    intent.putExtra("image1", tovar.getBig_image());
                    intent.putExtra("image2", tovar.getBig_image2());
                    intent.putExtra("image3", tovar.getBig_image3());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.setTransitionName("selected_tovar_image");
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, myViewHolder.tovar_image, view.getTransitionName());

                        mContext.startActivity(intent, optionsCompat.toBundle());
                    } else {
                        mContext.startActivity(intent);
                    }


                }
            });
        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar_loding.setIndeterminate(true);

        }



    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position)==null?VIEW_LOADING:VIEW_ITEM;
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

        MyViewHolder(View itemView) {
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
    class  LoadingViewHolder extends RecyclerView.ViewHolder{
     ProgressBar progressBar_loding;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar_loding=itemView.findViewById(R.id.progress_bar_more_loading);
        }
    }
}
