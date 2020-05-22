package com.akhmadov.saleschr2018.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.ShopAbout;
import com.akhmadov.saleschr2018.TovarsCategory.CategoryTovarsAdapter;
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

public class TovarsRecyclerFrag extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    CategoryTovarsAdapter adapter;
    private SearchView searchView;
    private ParseQueryTovars tovars_query;
    private static List<ParseObject> ob;
    private List<Tovar> tovars;
    private ProgressBar progressBar;
    private boolean isfollowing;
    private String shop_id;
    private String shop_name;
    private String shop_location;
    private String shop_img;
    private String shop_description;
    private String shop_tel;
    private String shop_inst;
    private int behavior = 0;
    private int limit = 16;
    private int skip = 0;
    private int orderBy = 0;
    private DialogFilter filter_dialog;
    private String search_str = "";
    private MainModelView mainModelView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.all_tovars_recycler, container, false);
        progressBar = view.findViewById(R.id.tovars_fragment_progressBar);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        String strtext = getArguments().getString("shop");
        shop_id = getArguments().getString("shop_id");
        shop_name = getArguments().getString("shop");
        shop_location = getArguments().getString("shop_location");
        shop_img = getArguments().getString("shop_img");
        shop_description = getArguments().getString("shop_description");
        shop_tel = getArguments().getString("shop_tel");
        shop_inst = getArguments().getString("shop_inst");
        toolbar.setTitle(strtext);
        swipeRefreshLayout = view.findViewById(R.id.shops_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
        recyclerView = view.findViewById(R.id.tovars_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CategoryTovarsAdapter(recyclerView, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(12);
        new RemoteDataTask().execute(0);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.setOnLikeClickListener(new CategoryTovarsAdapter.OnLikeClickListener() {
            @Override
            public void onClick(int position) {
                mainModelView.insertFavouriteTovar(tovars.get(position));
            }
        });
        adapter.setOnUnLikeClickListener(new CategoryTovarsAdapter.OnUnLikeClickListener() {
            @Override
            public void onClick(int position) {
                mainModelView.deleteFavouriteTovar(tovars.get(position).getUniqueId());

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        filter_dialog = new DialogFilter(requireContext());
        mainModelView = ViewModelProviders.of(this).get(MainModelView.class);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final MenuItem filterViewItem = menu.findItem(R.id.action_filter);
        final MenuItem infoViewItem = menu.findItem(R.id.action_info);
        final SearchView searchViewAndroidActionBar = (SearchView) searchViewItem.getActionView();
        searchViewAndroidActionBar.setQueryHint("Товар...");
        MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // filterViewItem.setVisible(false);
                infoViewItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // filterViewItem.setVisible(true);
                infoViewItem.setVisible(true);
                search_str = "";
                new RemoteDataTask().execute(orderBy);
                return true;
            }
        });

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                search_str = newText;
                if (!search_str.equals(""))
                    new RemoteDataTask().execute(orderBy);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_info:
                showPopup();
                break;
            case R.id.action_filter:
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
                break;

        }
        return true;
    }


    @SuppressLint("RestrictedApi")
    private void showPopup() {
        MenuBuilder menuBuilder = new MenuBuilder(requireContext());
        final MenuInflater inflater = new MenuInflater(getContext());
        inflater.inflate(R.menu.popupmenu, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(requireContext(), menuBuilder, getActivity().findViewById(R.id.action_info));
        optionsMenu.setForceShowIcon(true);
        if (shop_location == null || "null".equals(shop_location) || "".equals(shop_location))
            menuBuilder.findItem(R.id.popup_menu_map).setVisible(false);
        if (isfollowing) {
            menuBuilder.findItem(R.id.popup_menu_follow).setVisible(false);
            menuBuilder.findItem(R.id.popup_menu_unfollow).setVisible(true);
        } else {
            menuBuilder.findItem(R.id.popup_menu_unfollow).setVisible(false);
            menuBuilder.findItem(R.id.popup_menu_follow).setVisible(true);
        }

        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                Toast toast;
                TextView v;
                switch (item.getItemId()) {
                    case R.id.popup_menu_about:
                        Intent intent = new Intent(getContext(), ShopAbout.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("shop_id", shop_id);
                        bundle.putString("shop_name", shop_name);
                        bundle.putString("shop_location", shop_location);
                        bundle.putString("shop_img", shop_img);
                        bundle.putString("shop_description", shop_description);
                        bundle.putString("shop_tel", shop_tel);
                        bundle.putString("shop_inst", shop_inst);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return true;
                    case R.id.popup_menu_follow:
                        isfollowing = true;
                        toast = Toast.makeText(getContext(), "Вы подписались на обновления \n магазина: \"" + shop_name + "\"", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        v = toast.getView().findViewById(android.R.id.message);
                        if (v != null) v.setGravity(Gravity.CENTER);
                        toast.show();

                        return true;
                    case R.id.popup_menu_unfollow:
                        isfollowing = false;
                        toast = Toast.makeText(getContext(), "Вы отписались от обновлений \n магазина: \"" + shop_name + "\"", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        v = toast.getView().findViewById(android.R.id.message);
                        if (v != null) v.setGravity(Gravity.CENTER);
                        toast.show();

                        return true;
                    case R.id.popup_menu_map:
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + shop_location);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    default:
                        return false;
                }

            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });


        // Display the menu
        optionsMenu.show();
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
        new RemoteDataTask().execute(orderBy);
    }

    public class RemoteDataTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            isfollowing = false;
            orderBy = params[0];
            tovars = new ArrayList<>();
            tovars_query = new ParseQueryTovars(
                    "Tovars");

            if (!search_str.equals("")) {
                tovars_query.whereMatches("tovar_opisanie", search_str, "i");
                ParseQueryTovars parseQueryTovars = new ParseQueryTovars("Tovars");
                parseQueryTovars.whereMatches("tovar_name", search_str, "i");
                ParseQuery mainQuery = tovars_query.setMultipleQuery(parseQueryTovars, orderBy);
                mainQuery.include("shop_object");
                mainQuery.setLimit(limit);
                mainQuery.whereContains("shop_id", shop_id);
                try {
                    ob = mainQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                tovars_query.whereContains("shop_id", shop_id);
                tovars_query.setLimit(limit);
                tovars_query.include("shop_object");
                tovars_query.setOrderBy(orderBy);
                try {
                    ob = tovars_query.find();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
            tovars = ParseLoad.RetrievingTovars(ob);
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
                    int type = adapter.getItemViewType(position);
                    if (type == 0)
                        return 2;
                    else
                        return 1;
                }
            });
            if (tovars.size() <= 8) {
                adapter.setIsloading(true);
            }
            adapter.setLoadMore(new ILoadMore() {
                @Override
                public void MoreLoad() {
                    tovars.add(null);
                    adapter.notifyItemChanged(tovars.size() - 1);
                    new RemoteOnLoad().execute();
                }
            });
        }
    }

    public class RemoteOnLoad extends AsyncTask<Void, Void, Void> {
        private int itemStart = 0;
        private int itemsCount = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            skip += limit;
            tovars_query = new ParseQueryTovars(
                    "Tovars");
            if (!search_str.equals("")) {
                tovars_query.whereMatches("tovar_opisanie", search_str, "i");
                ParseQueryTovars parseQueryTovars = new ParseQueryTovars("Tovars");
                parseQueryTovars.whereMatches("tovar_name", search_str, "i");
                ParseQuery mainQuery = tovars_query.setMultipleQuery(parseQueryTovars, orderBy);
                mainQuery.include("shop_object");
                mainQuery.setLimit(limit);
                mainQuery.whereContains("shop_id", shop_id);
                try {
                    ob = mainQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                tovars_query.whereContains("shop_id", shop_id);
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
            itemStart = tovars.size() - 1;
            itemsCount = ob.size();
            tovars.remove(itemStart);
            tovars.addAll(ParseLoad.RetrievingTovars(ob));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyItemRangeChanged(itemStart, itemsCount);
            if (itemsCount < limit)
                adapter.setIsloading(true);
            else
                adapter.setIsloading(false);
        }
    }
}
