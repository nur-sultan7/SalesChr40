package com.akhmadov.saleschr2018.fragments;

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

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Tovar;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TovarsFrag extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Shop> shops;
    private ListView listView;
    TovarsAdapter adapter;
    SearchView searchView;
    private static List<ParseObject> ob;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String shop_id;
    String shop_name;
    private List<Tovar> tovars;
    private ProgressBar progressBar;

    public TovarsFrag() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        MenuItem infoViewItem = menu.findItem(R.id.action_info);
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

        View view = inflater.inflate(R.layout.activity_main_tovars, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.tovars_fragment_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        String strtext = getArguments().getString("shop");
        shop_id = getArguments().getString("shop_id");
        shop_name = getArguments().getString("shop_name");
        toolbar.setTitle(strtext);
        // Inflate the layout for this fragment
        listView = (ListView) view.findViewById(R.id.tovars_category_recycler_view);
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
            List<Tovar> tovars_ataginski = new ArrayList<>();
            tovars = new ArrayList<>();

            ParseQuery<ParseObject> tovars_query = new ParseQuery<ParseObject>(
                    "Tovars");
            tovars_query.whereContains("shop_id", shop_id);
            tovars_query.orderByDescending("_created_at");
            try {
                ob = tovars_query.find();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            for (final ParseObject tovar_parse : ob) {
                final Tovar tovar = new Tovar();
                ParseFile imageAsk = (ParseFile) tovar_parse.get("tovar_image");
                tovar.setImage(imageAsk.getUrl());
                ParseFile imageAsk2 = (ParseFile) tovar_parse.get("tovar_big_image");
                tovar.setBig_image(imageAsk2.getUrl());
                tovar.setName(String.valueOf(tovar_parse.get("tovar_name")));
                tovar.setSkidka(String.valueOf(tovar_parse.get("tovar_skidka")));
                tovar.setNew_cena(String.valueOf(tovar_parse.get("tovar_new_price")));
                tovar.setOld_cena(tovar_parse.getString("tovar_old_price"));
                tovar.setId_tovar(tovar_parse.getObjectId());
                tovar.setOpisanie(tovar_parse.getString("tovar_opisanie"));
                tovar.setShop_name(shop_name);
                tovar.setShop_id(shop_id);
                // shop_4show.setLocation(String.valueOf(shop_parse.get("shop_location")));
                tovars.add(tovar);
            }

          /*  ValueEventListener valueEventListener = new ValueEventListener() {
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
                    .orderByChild("shop_id")
                    .equalTo(shop_id);
            query.addListenerForSingleValueEvent(valueEventListener);*/
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            adapter = new TovarsAdapter(getActivity(), tovars);
            listView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
