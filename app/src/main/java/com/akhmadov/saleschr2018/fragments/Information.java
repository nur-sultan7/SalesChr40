package com.akhmadov.saleschr2018.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.akhmadov.saleschr2018.FavouriteCategory.FavouriteTovars;
import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Shop;

import java.util.List;

/**
 * Created by Akhmadov on 06.11.2017.
 */

public class Information extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ConstraintLayout constraintLayoutInstagram;

    private String mParam1;
    private String mParam2;

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
        constraintLayoutInstagram=view.findViewById(R.id.constraintLayoutInstagram);

        constraintLayoutInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAiguilleur ;
                String scheme = "http://instagram.com/_u/"+getString(R.string.official_instagram);
                String path = "https://instagram.com/"+getString(R.string.official_instagram);
                String nomPackageInfo ="com.instagram.android";
                try {
                  //  requireActivity().getPackageManager().getPackageInfo(nomPackageInfo, 0);
                    intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme));
                    intentAiguilleur.setPackage(nomPackageInfo);
                } catch (Exception e) {
                    intentAiguilleur = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                }
                startActivity(intentAiguilleur);
            }
        });

        return view;

    }
}
