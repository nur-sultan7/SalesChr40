package com.akhmadov.saleschr2018.TovarsCategory;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.akhmadov.saleschr2018.R;

import java.util.ArrayList;

/**
 * Created by Akhmadov on 06.11.2017.
 */

public class InnerTovarsCategoriesAdapter extends RecyclerView.Adapter<InnerTovarsCategoriesAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Category> Categorieslist = null;
    private ArrayList<Category> arraylist;
    private Filter filter;

     InnerTovarsCategoriesAdapter(Context context,
                                  ArrayList<Category> Categorieslist) {
        this.context = context;
        this.Categorieslist = Categorieslist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(Categorieslist);
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
            filter = new CheeseFilter();
        return filter;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view =  mInflater.inflate(R.layout.tovars_categories_item,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Category category = Categorieslist.get(position);
        final String image_st = category.getImg();
        SvgLoader.pluck()
                .with((Activity) context)
                .load(image_st, holder.Img);
        holder.name.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, CategoryTovars.newInstance(category.getId(),category.getName())).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return Categorieslist.size();
    }

    public class CheeseFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults newFilterResults = new FilterResults();

            if (constraint.length() > 0) {
                ArrayList<Category> auxData = new ArrayList<>();
                for (int i = 0; i < arraylist.size(); i++) {
                    if (arraylist.get(i).getName().toLowerCase().contains(constraint))
                        auxData.add(arraylist.get(i));
                }

                newFilterResults.count = auxData.size();
                newFilterResults.values = auxData;
            } else {

                newFilterResults.count = arraylist.size();
                newFilterResults.values = arraylist;
            }

            return newFilterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayList<Category> resultData = new ArrayList<>();
            resultData = (ArrayList<Category>) results.values;
            Categorieslist = resultData;
            notifyDataSetChanged();
        }

    }


  static class MyViewHolder extends RecyclerView.ViewHolder{
      ImageView Img;
      TextView name;



      MyViewHolder(View itemView) {
          super(itemView);
          Img=itemView.findViewById(R.id.tovars_category_img);
          name=itemView.findViewById(R.id.tovars_category_name);

      }
  }

}


