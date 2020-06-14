package com.akhmadov.saleschr2018.FollowingsCategory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akhmadov.saleschr2018.FollowingsCategory.adapters.FragmentFollowingAdapter;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.MainModelView;
import com.akhmadov.saleschr2018.data.Tovar;
import com.akhmadov.saleschr2018.fragments.TovarsRecyclerFrag;
import com.akhmadov.saleschr2018.libs.ParseLoad;
import com.akhmadov.saleschr2018.libs.ParseQueryTovars;
import com.akhmadov.saleschr2018.utils.DataUtil;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentFollowings extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private MainModelView mainModelView;
    private ArrayList<String> idsOfShops;
    private static List<Tovar> tovarList;
    private static List<ParseObject> parseObjects;
    private static FragmentFollowingAdapter  adapter;
    private static RecyclerView recyclerView;
    private  TextView textViewEmpty;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isCalled=false;

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
        adapter=new FragmentFollowingAdapter();
        adapter.setActivityDisplayMetrics(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.activity_main_sales, container, false);
        textViewEmpty=view.findViewById(R.id.empty_view);
        recyclerView=view.findViewById(R.id.shops_recycler);
        progressBar=view.findViewById(R.id.shops_fragment_progressBar);
        swipeRefreshLayout=view.findViewById(R.id.shops_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemViewCacheSize(9);
        recyclerView.setAdapter(adapter);
        if (!isCalled) {
            progressBar.setVisibility(View.VISIBLE);
            dataLoad();
            isCalled=true;
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.setOnItemClickListener(new FragmentFollowingAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, ImageView imageView, int position, String currentImage) {
                Intent intent = DataUtil.setIntentTovarCardView(getContext(), 1, adapter.getItemByPosition(position), currentImage);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTransitionName("selected_tovar_image");
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), imageView, view.getTransitionName());
                    startActivity(intent, optionsCompat.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        adapter.setOnShopClickListener(new FragmentFollowingAdapter.OnShopClickListener() {
            @Override
            public void onClick(String shopId, String shopName, String shopImage) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Fragment tovarsFrag = new TovarsRecyclerFrag();
                Fragment currentFragment = fragmentManager.findFragmentByTag("following");
                Bundle bundle = new Bundle();
                bundle.putString("shop", shopName);
                bundle.putString("shop_id", shopId);
                bundle.putString("shop_img", shopImage);
                tovarsFrag.setArguments(bundle);

                assert currentFragment != null;
                fragmentManager.beginTransaction().add(R.id.container, tovarsFrag).hide(currentFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @SuppressWarnings("unchecked")
    private void dataLoad()
    {
        idsOfShops.clear();
        idsOfShops.addAll(mainModelView.getAllFollowingShopsIds());
        new LoadFollowingShopsTovars().execute(idsOfShops);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        swipeRefreshLayout.setRefreshing(true);
        dataLoad();
    }
   @SuppressLint("StaticFieldLeak")
   protected  class  LoadFollowingShopsTovars extends AsyncTask<ArrayList<String>,Void, List<Tovar>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @SafeVarargs
        @Override
        protected final List<Tovar> doInBackground(ArrayList<String>... lists) {
            if (lists[0]!=null && lists[0].size()>0)
            {
                ParseQueryTovars queryTovars = new ParseQueryTovars("Tovars");
                queryTovars.whereContainedIn("shop_id",lists[0]);
                queryTovars.orderByDescending("_created_at");
                queryTovars.setLimit(20);
                queryTovars.include("shop_object");
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
            if (tovars!=null) {
                adapter.setTovarList(tovars);
            }
            else
            {
                textViewEmpty.setVisibility(View.VISIBLE);
                textViewEmpty.setText("Подписки отсутствуют");
                recyclerView.setVisibility(View.GONE);
            }
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
