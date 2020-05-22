package com.akhmadov.saleschr2018.TovarsCategory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.libs.TabId;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class InnerTovarsCategories extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Category> categories;
    private List<ParseObject> ob;
    private String tab;
    private String tab_id="0";
    private String cat_id;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    static Fragment newInstance(String cat_id, String tab)
    {
        Fragment tovarsFrag = new InnerTovarsCategories();
        Bundle bundle = new Bundle();
        bundle.putString("cat_id", cat_id);
        bundle.putString("tab",tab);
        tovarsFrag.setArguments(bundle);
        return tovarsFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tab = getArguments().getString("tab");
        }
        if (tab!=null)
            tab_id= TabId.GetTabId(tab);
        if (getArguments() != null) {
            cat_id = getArguments().getString("cat_id");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.activity_main_tovars,container,false);
        recyclerView=  rootView.findViewById(R.id.tovars_category_recycler_view);
        progressBar = rootView.findViewById(R.id.tovars_category_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout=rootView.findViewById(R.id.tovars_category_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        new RemoteDataTask().execute();
        return rootView;
    }

    @Override
    public String toString() {
        return getArguments().getString("tab");
    }

    @Override
    public void onRefresh() {
        new RemoteDataTask().execute();
    }

    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            categories = new ArrayList<>();
            ParseQuery<ParseObject> categories_query = new ParseQuery<ParseObject>(
                    "TovarsCategory");
            categories_query.orderByAscending("_created_at");
            if (cat_id!=null) {
                categories_query.whereContains("root", cat_id);
                categories_query.whereContains("tab_id", tab_id);
            }
            try {
                ob = categories_query.find();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            for (final ParseObject category_parse : ob) {
                final Category category = new Category();
                ParseFile imageAsk = (ParseFile) category_parse.get("img");
                category.setImg(imageAsk.getUrl());
                category.setName(category_parse.getString("name"));
                category.setId(category_parse.getObjectId());
                category.setRoot(category_parse.getString("root"));
                categories.add(category);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            InnerTovarsCategoriesAdapter adapter = new InnerTovarsCategoriesAdapter(getActivity(), categories);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemViewCacheSize(14);
            recyclerView.setAdapter(adapter);
            //swipeRefreshLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}