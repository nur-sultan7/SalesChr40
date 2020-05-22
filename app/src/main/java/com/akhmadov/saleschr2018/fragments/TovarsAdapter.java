package com.akhmadov.saleschr2018.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhmadov.saleschr2018.DBHelper;
import com.akhmadov.saleschr2018.ImageLoader;
import com.akhmadov.saleschr2018.PhotoView;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Tovar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akhmadov on 15.10.2017.
 */

public class TovarsAdapter extends BaseAdapter implements Filterable {

    // Declare Variables
    Context context;
    LayoutInflater inflater;

    private List<Tovar> Shopslist = null;
    private ArrayList<Tovar> arraylist;
    ImageLoader imageLoader;


    Filter filter;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ContentValues cv;




    public TovarsAdapter(Context context,
                        List<Tovar> Shopslist) {
        this.context = context;
        this.Shopslist = Shopslist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Tovar>();
        this.arraylist.addAll(Shopslist);
        imageLoader = new ImageLoader(context);
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

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

            if (constraint != null && constraint.length() > 0) {


                ArrayList<Tovar> auxData = new ArrayList<>();

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

            ArrayList<Tovar> resultData = new ArrayList<>();

            resultData = (ArrayList<Tovar>) results.values;

            //QuestionListViewAdapter adapter = new QuestionListViewAdapter(context, resultData);
            Shopslist = resultData;

            notifyDataSetChanged();
        }

    }


    public class ViewHolder {
        TextView shop_name;
        ImageView ic_add_fav;
        TextView location;
        ImageView Image;
        TextView new_cena;
        TextView old_cena;
        TextView skidka;
        LikeButton add_fav;
        boolean check;
        TextView first_name;
    }

    @Override
    public int getCount() {
        return Shopslist.size();
    }

    @Override
    public Tovar getItem(int position) {
        return Shopslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.tovars_item, null);
            holder.Image = (ImageView) view.findViewById(R.id.shop_image);
           holder.location = (TextView) view.findViewById(R.id.location_name);
            holder.shop_name =(TextView) view.findViewById(R.id.shop_name);
            holder.new_cena = (TextView) view.findViewById(R.id.tovar_new_cena);
            holder.old_cena=(TextView) view.findViewById(R.id.tovar_old_cena);
            holder.skidka = (TextView) view.findViewById(R.id.tovar_skidka);
            holder.add_fav = (LikeButton) view.findViewById(R.id.add_fav);
            holder.ic_add_fav = (ImageView) view.findViewById(R.id.add_fav_ic);
          //  holder.first_name = (TextView) view.findViewById(R.id.tovar_first_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Tovar tovar = getItem(position);
        holder.skidka.setText("-"+tovar.getSkidka()+"%");
        holder.new_cena.setText(tovar.getNew_cena()+"р.");
        holder.shop_name.setText(tovar.getName());
        holder.old_cena.setText(tovar.getOld_cena()+"p.");
        holder.old_cena.setPaintFlags(holder.old_cena.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.location.setText(tovar.getOpisanie());

      final String image_st = tovar.getImage();
        //String image_st = shop.getImage();
        Picasso.get()
                .load(image_st)
                .into(holder.Image);
        cv = new ContentValues();
        holder.check =false;
        if (!checkFavoriteItem(tovar))
        {
            holder.add_fav.setLiked(false);
          //  holder.ic_add_fav.setImageResource(R.drawable.ic_turned_in_not_black_24dp);

        }
        else
        {
            holder.add_fav.setLiked(true);
            //holder.ic_add_fav.setImageResource(R.drawable.ic_turned_in_not_gold_24dp);
//holder.check=true;
        }
        holder.add_fav.setOnLikeListener(new OnLikeListener() {
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
        });

       /* holder.add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (!holder.check) {
    holder.ic_add_fav.setImageResource(R.drawable.ic_turned_in_not_gold_24dp);
    holder.check=true;
    cv.put("object_id", tovar.getId_tovar());
    cv.put("shop_id", tovar.getShop_id());
    cv.put("shop_name",tovar.getShop_name());
    cv.put("tovar_name",tovar.getName());
    cv.put("tovar_opisanie",tovar.getOpisanie());
    cv.put("tovar_image",tovar.getImage());
    cv.put("tovar_new_price", tovar.getNew_cena());
    cv.put("tovar_skidka",tovar.getSkidka());
    cv.put("tovar_old_price",tovar.getOld_cena());
    db.insert("favorites",null,cv);


}

                else
                {
                    holder.ic_add_fav.setImageResource(R.drawable.ic_turned_in_not_black_24dp);
                    holder.check =false;

                    String table = "favorites";
                    String whereClause = "object_id=?";
                    String[] whereArgs = new String[] { tovar.getId_tovar() };
                    db.delete(table, whereClause, whereArgs);

                }

            }
        });*/


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                PhotoView.setImage_drawable(image_st,tovar.getBig_image());
                Intent intent = new Intent(context, PhotoView.class);
                context.startActivity(intent);
            }
        });


        return view;
    }
    public boolean checkFavoriteItem(Tovar checkProduct) {
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
    }


}

