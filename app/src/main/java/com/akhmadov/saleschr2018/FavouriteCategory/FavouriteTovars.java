package com.akhmadov.saleschr2018.FavouriteCategory;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
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
import com.akhmadov.saleschr2018.libs.DialogFilter;
import com.akhmadov.saleschr2018.utils.DataUtil;

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
    private DialogFilter filter_dialog;

    private static String orderBy;
    private static int checkChoice;
    private static Context favContext;

    public static Context getFavContext() {
        return favContext;
    }

    public static void setFavContext(Context favContext) {
        FavouriteTovars.favContext = favContext;
    }

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
        filter_dialog = new DialogFilter(requireContext());
        filter_dialog.price_asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkChoice=2;
               setFavouriteByOrder("new_cena ASC");
            }
        });
        filter_dialog.price_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkChoice=1;
                setFavouriteByOrder("new_cena DESC");
            }
        });
        filter_dialog.skidka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkChoice=3;
                setFavouriteByOrder("skidka DESC");

            }
        });
        filter_dialog.by_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkChoice=0;
                setFavouriteByOrder("uniqueId DESC");
            }
        });
        orderBy="uniqueId DESC";
        checkChoice=0;
    }
    private void setFavouriteByOrder(String orderBy)
    {
        FavouriteTovars.orderBy =orderBy;
        loadDataByOrderBy(FavouriteTovars.orderBy);
        filter_dialog.dismiss();
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        MenuItem info = menu.findItem(R.id.action_info);
        info.setVisible(false);
        final MenuItem filter = menu.findItem(R.id.action_filter);

        MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                filter.setVisible(false);
                return true;
            }


            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                getActivity().invalidateOptionsMenu ();
              //  search_str = "";
                setFavouriteByOrder(orderBy);
                return true;
            }
        });

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
       switch(item.getItemId())
        {
            case R.id.action_filter:
                filter_dialog.showDialogFilter();
                filter_dialog.checkChoice(checkChoice);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.all_tovars_recycler, container, false);
        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Избранные товары");
        progressBar =  view.findViewById(R.id.tovars_fragment_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.tovars_recyclerview);
        swipeRefreshLayout =  view.findViewById(R.id.tovars_swipe_refresh_layout);
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
        adapter.setOnItemClickListener(new FavouriteTovarsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, ImageView imageView, int position, String currentImage) {
                Intent intent = DataUtil.setIntentTovarCardView(getContext(), 1, adapter.getItemByPosition(position), currentImage);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTransitionName("selected_tovar_image");
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView, view.getTransitionName());
                    startActivity(intent, optionsCompat.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        setFavContext(getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.GONE);
        //swipeRefreshLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        tovars = new ArrayList<>();
        loadDataByOrderBy(orderBy);
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        loadDataByOrderBy(orderBy);
    }
    private void loadDataByOrderBy(String orderBy)
    {
        tovars.clear();
        tovars.addAll(mainModelView.getFavouriteTovars(orderBy));
        adapter.setFavouriteTovarsList(tovars);
        swipeRefreshLayout.setRefreshing(false);
    }



}
