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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.MainModelView;
import com.akhmadov.saleschr2018.data.Tovar;
import com.akhmadov.saleschr2018.libs.DialogFilter;
import com.akhmadov.saleschr2018.libs.ILoadMore;
import com.akhmadov.saleschr2018.libs.ParseLoad;
import com.akhmadov.saleschr2018.libs.ParseQueryTovars;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CategoryTovars extends Fragment implements  SwipeRefreshLayout.OnRefreshListener  {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CategoryTovarsAdapter adapter;
    private SearchView searchView;
    private ParseQueryTovars tovars_query;
    private static List<ParseObject> ob;
    private List<Tovar> tovars;
    private ProgressBar progressBar;
    private  boolean isfollowing;
    private String cat_id;
    private String cat_name;
    private int limit=16;
    private int skip=0;
    private int orderBy=0;
    private DialogFilter filter_dialog;
    private String search_str="";
    private MainModelView mainModelView;



    public static Fragment newInstance(String cat_id, String cat_name) {
        Fragment fragment = new CategoryTovars();
        Bundle args = new Bundle();
        args.putString("cat_id", cat_id);
        args.putString("cat_name", cat_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
          cat_id=getArguments().getString("cat_id");
          cat_name=getArguments().getString("cat_name");
        }
        filter_dialog=new DialogFilter(requireContext());
        mainModelView= ViewModelProviders.of(this).get(MainModelView.class);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final MenuItem infoViewItem = menu.findItem(R.id.action_info);
        final MenuItem filterViewItem = menu.findItem(R.id.action_filter);
        infoViewItem.setVisible(false);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setQueryHint("Товар...");
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search_str=newText;
                if (!search_str.equals(""))
                    new RemoteDataTask().execute(orderBy);
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
               // filterViewItem.setVisible(false);

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
               // filterViewItem.setVisible(true);
                search_str="";
                new RemoteDataTask().execute(orderBy);
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_info:
                break;
            case  R.id.action_filter:
                filter_dialog.showDialogFilter();
                filter_dialog.checkChoice(orderBy);
                filter_dialog.price_asc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new RemoteDataTask().execute(2);
                        filter_dialog.dismiss();
                    }
                });
                filter_dialog.price_desc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new RemoteDataTask().execute(1);
                        filter_dialog.dismiss();
                    }
                });
               filter_dialog.skidka.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new RemoteDataTask().execute(3);
                        filter_dialog.dismiss();
                    }
                });
               filter_dialog.by_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new RemoteDataTask().execute(0);
                        filter_dialog.dismiss();
                    }
                });
                break;
            default:
                    return true;


        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.all_tovars_recycler, container, false);
        progressBar = view.findViewById(R.id.tovars_fragment_progressBar) ;
        progressBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        cat_name=getArguments().getString("cat_name");
        cat_id=getArguments().getString("cat_id");
        toolbar.setTitle(cat_name);
        swipeRefreshLayout = view.findViewById(R.id.shops_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
        recyclerView= view.findViewById(R.id.tovars_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        //listView.addItemDecoration( new LayoutMarginDecoration( 2, 10 ) );
        adapter = new CategoryTovarsAdapter(mainModelView,recyclerView,this.getContext());
        adapter.setFromCategory(1);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(12);
        adapter.setOnLikeClickListener(new CategoryTovarsAdapter.OnLikeClickListener() {
            @Override
            public void onClick(int position) {
                Tovar tovar = tovars.get(position);
                tovar.setIs_fav(true);
                mainModelView.insertFavouriteTovar(tovar);
            }
        });
        adapter.setOnUnLikeClickListener(new CategoryTovarsAdapter.OnUnLikeClickListener() {
            @Override
            public void onClick(int position) {
                mainModelView.deleteFavouriteTovar(tovars.get(position).getId_tovar());

            }
        });
        new RemoteDataTask().execute(orderBy);
        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new RemoteDataTask().execute(orderBy);
    }

    public class RemoteDataTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            isfollowing=false;
            skip=0;
            orderBy=params[0];
            tovars = new ArrayList<>();
            tovars_query = new ParseQueryTovars(
                    "Tovars");
            if (!search_str.equals("")) {
                tovars_query.whereMatches("tovar_opisanie",search_str,"i");
                ParseQueryTovars parseQueryTovars = new ParseQueryTovars("Tovars");
                parseQueryTovars.whereMatches("tovar_name",search_str,"i");
                ParseQuery mainQuery = tovars_query.setMultipleQuery(parseQueryTovars,orderBy);
                mainQuery.include("shop_object");
                mainQuery.setLimit(limit);
                mainQuery.whereContains("cat_id",cat_id);
                try
                { ob=mainQuery.find(); }
                catch (ParseException e)
                { e.printStackTrace(); }
            }
            else {
                tovars_query.setOrderBy(orderBy);
                tovars_query.whereContains("cats_id", cat_id);
                tovars_query.include("shop_object");
                tovars_query.setLimit(limit);
                try {
                    ob = tovars_query.find();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
           tovars= ParseLoad.RetrievingTovars(ob);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            adapter.setTovarsList(tovars);
            swipeRefreshLayout.setRefreshing(false);
            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type=adapter.getItemViewType(position);
                    if (type == 0)
                        return 2;
                    else
                        return 1;
                }
            });
            if (tovars.size()<=8)
            {
                adapter.setIsloading(true);
            }
            adapter.setLoadMore(new ILoadMore() {
                @Override
                public void MoreLoad() {
                    tovars.add(null);
                    adapter.notifyItemChanged(tovars.size()-1);
                    new RemoteOnLoad().execute();
                }
            });
        }
    }
    public class RemoteOnLoad extends AsyncTask<Void, Void, Void> {
        private int itemStart=0;
        private int itemsCount=0;
        @Override
        protected Void doInBackground(Void... voids) {
            skip+=limit;
            tovars_query = new ParseQueryTovars(
                    "Tovars");
            if (!search_str.equals("")) {
                tovars_query.whereMatches("tovar_opisanie",search_str,"i");
                ParseQueryTovars parseQueryTovars = new ParseQueryTovars("Tovars");
                parseQueryTovars.whereMatches("tovar_name",search_str,"i");
                ParseQuery mainQuery = tovars_query.setMultipleQuery(parseQueryTovars,orderBy);
                mainQuery.include("shop_object");
                mainQuery.setLimit(limit);
                mainQuery.whereContains("cat_id",cat_id);
                try
                { ob=mainQuery.find(); }
                catch (ParseException e)
                { e.printStackTrace(); }
            }
            else {
                tovars_query.whereContains("cats_id", cat_id);
                tovars_query.include("shop_object");
                tovars_query.setOrderBy(orderBy);
                tovars_query.setSkip(skip);
                tovars_query.setLimit(limit);
                try {
                    ob = tovars_query.find();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
            itemStart=tovars.size()-1;
            itemsCount=ob.size();
            tovars.remove(itemStart);
            tovars.addAll(ParseLoad.RetrievingTovars(ob));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyItemRangeChanged(itemStart,itemsCount);
            if (itemsCount<limit)
            adapter.setIsloading(true);
            else
                adapter.setIsloading(false);
        }
    }

}
