package com.akhmadov.saleschr2018.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.akhmadov.saleschr2018.R;

import java.util.ArrayList;
import java.util.List;

public class ShopsCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    private List<ShopCategory> Shopslist ;
    Filter filter;
    private static  final int TYPE_HEADER=0;
    private static final int TYPE_ITEN=1;

    public int getItemViewType(int position){
        if (position==0)
        return TYPE_HEADER;
        else
        return TYPE_ITEN;
    }

    ShopsCategoriesAdapter(Context context,
                 List<ShopCategory> Shopslist) {
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


                ArrayList<ShopCategory> auxData = new ArrayList<>();

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
            ArrayList<ShopCategory> resultData;
            resultData = (ArrayList<ShopCategory>) results.values;
            Shopslist = resultData;
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        if (viewType==TYPE_ITEN) {
            view = new MyViewHolder(mInflater.inflate(R.layout.shops_categories_item, parent, false));
        }
        else
        {
           view =  new HeaderHolder(mInflater.inflate(R.layout.shops_categories_header,parent,false));
        }
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if (holder.getItemViewType()==1) {
           final ShopCategory category = Shopslist.get(position);
           MyViewHolder myViewHolder = (MyViewHolder) holder;
           myViewHolder.shop_name.setText(category.getName());
           myViewHolder.Image.setImageResource(category.getDrawebleRes());
           myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AppCompatActivity activity = (AppCompatActivity) view.getContext();
                   Fragment tovarsFrag = new SalesMianFrag();
                   Bundle bundle = new Bundle();
                   bundle.putString("category_name", category.getName());
                   bundle.putInt("category_id", category.getId());
                   tovarsFrag.setArguments(bundle);
                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, tovarsFrag).addToBackStack(null).commit();

               }
           });
       }
       else
       {
           final HeaderHolder headerHolder = (HeaderHolder) holder;
           headerHolder.all_categories.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AppCompatActivity activity = (AppCompatActivity) view.getContext();
                   Fragment tovarsFrag = new SalesMianFrag();
                   Bundle bundle = new Bundle();
                   bundle.putString("category_name", (String) headerHolder.all_categories.getText());
                   bundle.putInt("category_id", 0);
                   tovarsFrag.setArguments(bundle);
                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, tovarsFrag).addToBackStack(null).commit();
               }
           });
       }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return Shopslist.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView shop_name;
        ImageView Image;

        MyViewHolder(View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.shops_categories_image);
            shop_name = itemView.findViewById(R.id.shops_categories_name);
        }
    }
    public static class HeaderHolder extends RecyclerView.ViewHolder{

        Button all_categories;
        HeaderHolder(@NonNull View itemView) {
            super(itemView);
            all_categories = itemView.findViewById(R.id.shops_categories_all_shops);
        }
    }


}

