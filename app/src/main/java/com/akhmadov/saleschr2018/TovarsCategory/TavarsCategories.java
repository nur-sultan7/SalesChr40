package com.akhmadov.saleschr2018.TovarsCategory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Shop;
import com.akhmadov.saleschr2018.fragments.TovarsFrag;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akhmadov on 06.11.2017.
 */

public class TavarsCategories extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Shop> shops;
    private RecyclerView listView;
    TovarsCategoriesAdapter adapter;
    SearchView searchView;
    private ArrayList<Category> categories;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    public ParseQuery<ParseObject> categories_query;
    public static List<ParseObject> ob;
    private String mParam1;
    private String mParam2;

    public static TovarsFrag newInstance(String param1, String param2) {
        TovarsFrag fragment = new TovarsFrag();
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
        MenuItem infoItem = menu.findItem(R.id.action_info);
        MenuItem filterItem = menu.findItem(R.id.action_filter);
        filterItem.setVisible(false);
        infoItem.setVisible(false);



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
        toolbar.setTitle("Товары");
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tovars_category_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener( this);
        progressBar = (ProgressBar) view.findViewById(R.id.tovars_category_progressBar) ;
        progressBar.setVisibility(View.VISIBLE);


        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);



                                    }
                                }
        );
        listView=  view.findViewById(R.id.tovars_category_recycler_view);
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

    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override


        protected Void doInBackground(Void... params) {



            categories = new ArrayList<>();

            categories_query = new ParseQuery<ParseObject>(
                    "TovarsCategory");
            categories_query.orderByAscending("_created_at");
            categories_query.whereDoesNotExist("root");
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
                category.setTabs( category_parse.<String>getList("tabs"));
                categories.add(category);
            }
            /*ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tovars.clear();
                    if (dataSnapshot.exists())
                    {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                            Tovar shop = snapshot.getValue(Tovar.class);
                            tovars.add(shop);
                        }
                        Collections.reverse(tovars);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            Query query = FirebaseDatabase.getInstance().getReference("tovars")
                    .orderByChild("time");

            query.addListenerForSingleValueEvent(valueEventListener);*/



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
            progressBar.setVisibility(View.GONE);
            adapter = new TovarsCategoriesAdapter(getActivity(),  categories);
            listView.setLayoutManager(new LinearLayoutManager(getContext()));
            //listView.addItemDecoration( new LayoutMarginDecoration( 2, 10 ) );

            listView.setItemViewCacheSize(14);

            listView.setAdapter(adapter);
            //swipeRefreshLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}