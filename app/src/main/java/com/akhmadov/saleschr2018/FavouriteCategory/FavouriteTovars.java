package com.akhmadov.saleschr2018.FavouriteCategory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akhmadov.saleschr2018.DBHelper;
import com.akhmadov.saleschr2018.FavouriteCategory.adapters.FavouriteTovarsAdapter;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.MainModelView;
import com.akhmadov.saleschr2018.data.Shop;
import com.akhmadov.saleschr2018.data.Tovar;

import java.util.ArrayList;
import java.util.List;


public class FavouriteTovars extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Shop> shops;
    private List<Tovar> tovars;
    private RecyclerView recyclerView;
    private FavouriteTovarsAdapter adapter;
    SearchView searchView;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues cv;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MainModelView mainModelView;

    private String mParam1;
    private String mParam2;


    public static FavouriteTovars newInstance(String param1, String param2) {
        FavouriteTovars fragment = new FavouriteTovars();
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
        mainModelView = ViewModelProviders.of(this).get(MainModelView.class);
        adapter = new FavouriteTovarsAdapter();
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
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
            //    adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.all_tovars_recycler, container, false);
        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Избранные товары");
        progressBar =  view.findViewById(R.id.tovars_fragment_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.tovars_recyclerview);
        swipeRefreshLayout =  view.findViewById(R.id.shops_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnLikeClickListener(new FavouriteTovarsAdapter.OnLikeClickListener() {
            @Override
            public void onClick(int position) {
            }
        });
        adapter.setOnUnLikeClickListener(new FavouriteTovarsAdapter.OnUnLikeClickListener() {
            @Override
            public void onClick(int position) {
                mainModelView.deleteFavouriteTovar(adapter.getItemByPosition(position).getId_tovar());
                adapter.deleteByPosition(position);
            }
        });
      //  new RemoteDataTask().execute();
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.GONE);
        //swipeRefreshLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        List<Tovar> results = new ArrayList<>();
        results.addAll(mainModelView.getFavouriteTovars());
        adapter.setFavouriteTovarsList(results);
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new RemoteDataTask().execute();
    }


   public class RemoteDataTask extends AsyncTask<Void, Void, List<Tovar>> {
        @Override
        protected List<Tovar> doInBackground(Void... params) {
           List<Tovar> results = new ArrayList<>();
           results.addAll(mainModelView.getFavouriteTovars());
           return results;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }

        protected void onPostExecute(List<Tovar> result) {
            adapter.setFavouriteTovarsList(result);
        }

    }
}
