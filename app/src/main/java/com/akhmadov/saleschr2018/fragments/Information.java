package com.akhmadov.saleschr2018.fragments;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.akhmadov.saleschr2018.FavouriteCategory.FavouriteTovars;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Shop;

import java.util.List;

/**
 * Created by Akhmadov on 06.11.2017.
 */

public class Information extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Shop> shops;
    ListView listView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    
    // TODO: Rename and change types and number of parameters
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

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info, container, false);
        return view;

    }
}
