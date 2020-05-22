package com.akhmadov.saleschr2018.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.akhmadov.saleschr2018.DBHelper;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Tovar;

import java.util.ArrayList;
import java.util.List;

////NEVER USE/////////////////
public class FavFrag extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Shop> shops;
    List<Tovar> tovars;
    ListView listView;
    FavAdapter adapter;
    SearchView searchView;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ContentValues cv;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalesMianFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FavFrag newInstance(String param1, String param2) {
        FavFrag fragment = new FavFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
View view =inflater.inflate(R.layout.activity_main_tovars, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
     //   toolbar.setTitle("Избранные товары");
        progressBar = (ProgressBar) view.findViewById(R.id.tovars_fragment_progressBar) ;
        progressBar.setVisibility(View.VISIBLE);
        // Inflate the layout for this fragment
        listView= (ListView) view.findViewById(R.id.tovars_category_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.shops_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
new RemoteDataTask().execute();
        return view;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
new RemoteDataTask().execute();
    }

    // TODO: Rename method, update argument and hook method into UI event

    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            dbHelper = new DBHelper(getActivity());
            db = dbHelper.getWritableDatabase();
            cv = new ContentValues();
            tovars=new ArrayList<>();
            Cursor c = db.query("favorites", null, null, null, null, null, null);
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int idColIndex = c.getColumnIndex("id");
                int objecr_id_ColIndex = c.getColumnIndex("object_id");
                int shop_id_ColIndex = c.getColumnIndex("shop_id");
                int shop_name_ColIndex = c.getColumnIndex("shop_name");
                int tovar_opisanie_ColIndex = c.getColumnIndex("tovar_opisanie");
                int tovar_name_ColIndex = c.getColumnIndex("tovar_name");
                int tovar_image_ColIndex = c.getColumnIndex("tovar_image");
                int tovar_big_image_ColIndex = c.getColumnIndex("tovar_big_image");
                int tovar_new_price_ColIndex = c.getColumnIndex("tovar_new_price");
                int tovar_skidki_ColIndex = c.getColumnIndex("tovar_skidka");
                int tovar_old_price_ColIndex = c.getColumnIndex("tovar_old_price");
                do {
                    Tovar tovar = new Tovar();
                    tovar.setId_tovar(c.getString(objecr_id_ColIndex));
                    tovar.setShop_id(c.getString(shop_id_ColIndex));
                    tovar.setShop_name(c.getString(shop_name_ColIndex));
                    tovar.setOpisanie(c.getString(tovar_opisanie_ColIndex));
                    tovar.setName(c.getString(tovar_name_ColIndex));
                    tovar.setImage(c.getString(tovar_image_ColIndex));
                    tovar.setBig_image(c.getString(tovar_big_image_ColIndex));
                    tovar.setNew_cena(c.getString(tovar_new_price_ColIndex));
                    tovar.setSkidka(c.getString(tovar_skidki_ColIndex));
                    tovar.setOld_cena(c.getString(tovar_old_price_ColIndex));
                    tovars.add(0,tovar);
                } while (c.moveToNext());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // progressBar.setVisibility(View.VISIBLE);
        }

        //	@Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            // listview = (ListView) getActivity().findViewById(R.id.listviewquestion);
            // Pass the results into ListViewAdapter.java
            //
            adapter = new FavAdapter(getActivity(), tovars);
            listView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
            //swipeRefreshLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}
