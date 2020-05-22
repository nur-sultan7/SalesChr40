package com.akhmadov.saleschr2018.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akhmadov.saleschr2018.DBHelper;
import com.akhmadov.saleschr2018.ImageLoader;
import com.akhmadov.saleschr2018.MemoryCache;
import com.akhmadov.saleschr2018.PhotoView;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Tovar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akhmadov on 15.10.2017.
 */

public class FavAdapter extends BaseAdapter implements Filterable {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    MemoryCache memoryCache = new MemoryCache();
    ImageLoader imageLoader = new ImageLoader(context);

    private List<Tovar> Fav_tov_list = null;
    private ArrayList<Tovar> arraylist;
    Bitmap bitmap;
    Filter filter;
    DBHelper dbHelper ;
    SQLiteDatabase db;




    public FavAdapter(Context context,
                      List<Tovar> Shopslist) {
        this.context = context;
        this.Fav_tov_list = Shopslist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Tovar>();
        if (!(Shopslist==null))
        this.arraylist.addAll(Shopslist);
        dbHelper = new DBHelper(context);
        db=dbHelper.getWritableDatabase();

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
            Fav_tov_list = resultData;

            notifyDataSetChanged();
        }

    }


    public class ViewHolder {
        TextView shop_name;
        TextView opisanie;
        ImageView Image;
        TextView cena;
        TextView skidka;
        TextView shop;
        RelativeLayout del;
        TextView old_cena;
    }

    @Override
    public int getCount() {
        return Fav_tov_list.size();
    }

    @Override
    public Tovar getItem(int position) {
        return Fav_tov_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fav_tovars_item, null);
            holder.Image = (ImageView) view.findViewById(R.id.shop_image);
            holder.opisanie = (TextView) view.findViewById(R.id.tovar_opisanie);
            holder.shop_name =(TextView) view.findViewById(R.id.shop_name);
            holder.cena = (TextView) view.findViewById(R.id.tovar_cena);
            holder.skidka = (TextView) view.findViewById(R.id.tovar_skidka);
            holder.shop = (TextView) view.findViewById(R.id.shop_name2);
            holder.del = (RelativeLayout) view.findViewById(R.id.del_fav);
            holder.old_cena = (TextView) view.findViewById(R.id.tovar_old_cena);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Tovar tovar = getItem(position);
        holder.skidka.setText("-"+tovar.getSkidka()+"%");
        holder.cena.setText(tovar.getNew_cena()+"р.");
        holder.shop_name.setText(tovar.getName());
        holder.opisanie.setText(tovar.getOpisanie());
        holder.shop.setText(tovar.getShop_name());
     final String image_st = tovar.getImage();
        holder.old_cena.setText(tovar.getOld_cena());
        holder.old_cena.setText(tovar.getOld_cena()+"p.");
        holder.old_cena.setPaintFlags(holder.old_cena.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

       // int resId = view.getResources().getIdentifier(image_st, "drawable", "com.akhmadov.saleschr20");
     //   bitmap=memoryCache.get(image_st);
       // holder.Image.setImageBitmap(bitmap);
        Picasso.get()
                .load(image_st)
                .into(holder.Image);


        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fav_tov_list.remove(position);
                notifyDataSetChanged();
                String table = "favorites";
                String whereClause = "object_id=?";
                String[] whereArgs = new String[] { tovar.getId_tovar() };
                db.delete(table, whereClause, whereArgs);
               // sharedPreference.remTovar(context,position);

            }
        });
/*
        switch (shop.getImage()){
            case "lenta":
                holder.Image.setImageResource(R.drawable.lenta);
                break;
            case "ataginski":
                holder.Image.setImageResource(R.drawable.ataginski);
                break;
            case "assorti":
                holder.Image.setImageResource(R.drawable.assorti);
                break;

        }*/


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


}

