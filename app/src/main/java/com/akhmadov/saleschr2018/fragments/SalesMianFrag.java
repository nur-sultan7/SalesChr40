package com.akhmadov.saleschr2018.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.akhmadov.saleschr2018.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class SalesMianFrag extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Shop> shops;
    private RecyclerView listView;
    private TextView textView_empty;
    SalesAdapter adapter;
    SearchView searchView;
   private static AppCompatActivity ddd;
    public List<Shop> shops_list;
    private static List<ParseObject> ob;
    private SwipeRefreshLayout swipeRefreshLayout;
   private static ProgressBar progressBar;
    private String cat_name;
    private int cat_id;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ///// Fire Base



    public SalesMianFrag() {
        // Required empty public constructor
    }

    public static SalesMianFrag newInstance(String param1, String param2) {
        SalesMianFrag fragment = new SalesMianFrag();
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
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Магазины");
        ddd = (AppCompatActivity) getActivity();




    }

    @Override
    public void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(cat_name);


        // progressBar.setVisibility(View.GONE);
    }
     public void onResume(){
         super.onResume();
       //  progressBar.setVisibility(View.GONE);

     }




    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        MenuItem infoViewItem = menu.findItem(R.id.action_info);
        MenuItem filterViewItem = menu.findItem(R.id.action_filter);
        filterViewItem.setVisible(false);
        infoViewItem.setVisible(false);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
       // searchViewAndroidActionBar.setIconified(false);
       // searchViewAndroidActionBar.onActionViewExpanded();
        searchViewAndroidActionBar.setQueryHint("Магазин...");
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

View view =inflater.inflate(R.layout.activity_main_sales, container, false);


        listView=  view.findViewById(R.id.shops_recycler);
        textView_empty = view.findViewById(R.id.empty_view);


       // adapter = new SalesAdapter(getActivity(),shops);
       // listView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.shops_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.shops_fragment_progressBar) ;
        progressBar.setVisibility(View.VISIBLE);
       // progressBar.setVisibility(View.VISIBLE);
        //  swipeRefreshLayout.setRefreshing(false);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
        Bundle bundle = getArguments();
        assert bundle != null;
        cat_id = bundle.getInt("category_id");
        cat_name = bundle.getString("category_name");
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


            shops=new ArrayList<>();
            ParseQuery<ParseObject> shops_query = new ParseQuery<ParseObject>(
                    "Shops");
            shops_query.orderByDescending("_created_at");
            if (cat_id!=0)
            {
                shops_query.whereContains("category_id", String.valueOf(cat_id));
            }
            try {
                ob = shops_query.find();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            for (final ParseObject shop_parse : ob) {
                // Locate images in flag column
                final Shop shop_4show = new Shop();

                ParseFile imageAsk = (ParseFile) shop_parse.get("shop_image");
                shop_4show.setImage(imageAsk.getUrl());
                ParseFile imageAsk2 = (ParseFile) shop_parse.get("shop_big_image");
                shop_4show.setBig_image(imageAsk2.getUrl());
                shop_4show.setName(String.valueOf(shop_parse.get("shop_name")));
                shop_4show.setLocation(String.valueOf(shop_parse.get("shop_location")));
                shop_4show.setShop_id(shop_parse.getObjectId());
                shop_4show.setDescription(String.valueOf(shop_parse.get("shop_description")));
                shop_4show.setTel(shop_parse.getString("shop_tel"));
                shop_4show.setInst(shop_parse.getString("shop_inst"));
                shops.add(shop_4show);
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

          //  progressBar.setVisibility(View.VISIBLE);
        }

        //	@Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            // listview = (ListView) getActivity().findViewById(R.id.listviewquestion);
            // Pass the results into ListViewAdapter.java
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            listView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (shops.isEmpty())
            {
                listView.setVisibility(View.GONE);
                textView_empty.setVisibility(View.VISIBLE);
            }
            else {
                adapter = new SalesAdapter(getActivity(), shops);
                listView.setAdapter(adapter);
            }

        }

    }




}
