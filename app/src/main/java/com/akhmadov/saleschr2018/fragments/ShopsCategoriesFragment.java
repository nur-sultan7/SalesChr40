package com.akhmadov.saleschr2018.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.akhmadov.saleschr2018.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;


public class ShopsCategoriesFragment extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<ShopCategory> shops_categories;
    private RecyclerView listView;
    ShopsCategoriesAdapter adapter;

    private static AppCompatActivity ddd;

    private static List<ParseObject> ob;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;






    public static ShopsCategoriesFragment newInstance(String param1, String param2) {
        ShopsCategoriesFragment fragment = new ShopsCategoriesFragment();
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
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Магазины");
        ddd = (AppCompatActivity) getActivity();


    }

    @Override
    public void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Магазины");

    }
    public void onResume(){
        super.onResume();

    }


    public static  void change(String category_name, int category_id)
    {
        FragmentTransaction fragmentTransaction =  ddd.getSupportFragmentManager().beginTransaction();
        SalesMianFrag tovarsFrag = new SalesMianFrag();
        Bundle bundle = new Bundle();
        bundle.putString("category_name", category_name);
        bundle.putInt("category_id",category_id);
        tovarsFrag.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container,tovarsFrag);
        fragmentTransaction.commit();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.shops_categoriea_fragment, container, false);
        listView=  view.findViewById(R.id.shops_categories_recycler);
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

        new RemoteDataTask().execute();
    }


    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            shops_categories=new ArrayList<>();
            shops_categories.add(new ShopCategory("Все магазины"));
            shops_categories.add(new ShopCategory("Одежда и обувь",R.drawable.clothes_banner,1));
            shops_categories.add(new ShopCategory("Часы и бижутерия",R.drawable.watch_banner, 2));
            shops_categories.add(new ShopCategory("Аксесуары",R.drawable.acsessuars_banner,3));
            shops_categories.add(new ShopCategory("Электроника и бытовая техника",R.drawable.elec_banner,4));
            shops_categories.add(new ShopCategory("Детские товары",R.drawable.baby_banner,5));
            shops_categories.add(new ShopCategory("Дом и строительные материалы",R.drawable.house_banner,6));
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected void onPostExecute(Void result) {
            listView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new ShopsCategoriesAdapter(getActivity(),shops_categories);
            listView.setAdapter(adapter);

        }
    }
}
