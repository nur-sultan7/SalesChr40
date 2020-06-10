package com.akhmadov.saleschr2018.FollowingsCategory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.MainModelView;
import com.akhmadov.saleschr2018.data.Tovar;
import com.akhmadov.saleschr2018.libs.ParseLoad;
import com.akhmadov.saleschr2018.libs.ParseQueryTovars;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentFollowings extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private MainModelView mainModelView;
    private ArrayList<String> idsOfShops;
    private static List<Tovar> tovarList;
    private static List<ParseObject> parseObjects;

    public static FragmentFollowings newInstance ()
    {
        FragmentFollowings fragment = new FragmentFollowings();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
        {

        }
        mainModelView= ViewModelProviders.of(this).get(MainModelView.class);
        idsOfShops=new ArrayList<>();
        idsOfShops.addAll(mainModelView.getAllFollowingShopsIds());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.activity_main_sales container, false);
        new LoadFollowingShopsTovars().execute(idsOfShops);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }
    private static  class  LoadFollowingShopsTovars extends AsyncTask<ArrayList<String>,Void, List<Tovar>>
    {
        @SafeVarargs
        @Override
        protected final List<Tovar> doInBackground(ArrayList<String>... lists) {
            if (lists[0]!=null && lists[0].size()>0)
            {
                ParseQueryTovars queryTovars = new ParseQueryTovars("Tovars");
                queryTovars.whereContainedIn("shop_id",lists[0]);
                queryTovars.orderByDescending("_created_at");
                queryTovars.setLimit(20);
                try {
                     parseObjects= queryTovars.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tovarList= ParseLoad.RetrievingTovars(parseObjects);
            }
            return tovarList;
        }

        @Override
        protected void onPostExecute(List<Tovar> tovars) {
            super.onPostExecute(tovars);

        }
    }
}
