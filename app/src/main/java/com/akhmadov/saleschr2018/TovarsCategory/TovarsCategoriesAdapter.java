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
import com.akhmadov.saleschr2018.ImageLoader;
import com.akhmadov.saleschr2018.R;

import java.util.ArrayList;

/**
 * Created by Akhmadov on 06.11.2017.
 */

public class TovarsCategoriesAdapter extends RecyclerView.Adapter<TovarsCategoriesAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private LayoutInflater inflater;
    ImageLoader imageLoader = new ImageLoader(context);
    private ArrayList<Category> Categorieslist = null;
    private ArrayList<Category> arraylist;
    private Filter filter;
  //  private DBHelper dbHelper;
   // private SQLiteDatabase db;
   // private ContentValues cv;

     TovarsCategoriesAdapter(Context context,
                             ArrayList<Category> Categorieslist) {
        this.context = context;
        this.Categorieslist = Categorieslist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(Categorieslist);
       // dbHelper = new DBHelper(context);
        // db = dbHelper.getWritableDatabase();

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
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, InnerMainTovarsCategories.newInstance(category.getId(),category.getName(),category.getTabs())).addToBackStack(null).commit();
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

            //QuestionListViewAdapter adapter = new QuestionListViewAdapter(context, resultData);
            Categorieslist = resultData;

            notifyDataSetChanged();
        }

    }





     /*  cv = new ContentValues();
        holder.check =false;
        if (!checkFavoriteItem(tovar))
        {
            holder.ic_add_fav.setLiked(false);
        }
        else
        {
            holder.ic_add_fav.setLiked(true);
            holder.check=true;
        }
        holder.ic_add_fav.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                cv.put("object_id", tovar.getId_tovar());
                cv.put("shop_id", tovar.getShop_id());
                cv.put("shop_name",tovar.getShop_name());
                cv.put("tovar_name",tovar.getName());
                cv.put("tovar_opisanie",tovar.getOpisanie());
                cv.put("tovar_image",tovar.getImage());
                cv.put("tovar_big_image",tovar.getBig_image());
                cv.put("tovar_new_price", tovar.getNew_cena());
                cv.put("tovar_skidka",tovar.getSkidka());
                cv.put("tovar_old_price",tovar.getOld_cena());
                db.insert("favorites",null,cv);
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                String table = "favorites";
                String whereClause = "object_id=?";
                String[] whereArgs = new String[] { tovar.getId_tovar() };
                db.delete(table, whereClause, whereArgs);
            }
        });*/





  /* boolean checkFavoriteItem(Tovar checkProduct) {
        String table = "favorites";
        String[] columns = {"object_id", "shop_id","shop_name","tovar_name","tovar_image","tovar_big_image","tovar_new_price","tovar_skidka","tovar_old_price"};
        String selection = "object_id =?";
        String[] selectionArgs = {checkProduct.getId_tovar()};
        String groupBy = null;
        String having = null;
       // String orderBy = "column3 DESC";
        String orderBy =null;
        String limit = null;
        Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        boolean check = false;
        if (c.moveToFirst())
        {
            check=true;
        }
        c.close();
        return check;
    }*/
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


