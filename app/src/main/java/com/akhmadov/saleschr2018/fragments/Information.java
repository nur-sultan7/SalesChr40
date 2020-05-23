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

import com.akhmadov.saleschr2018.R;
import com.akhmadov.saleschr2018.data.Shop;

import java.util.List;

/**
 * Created by Akhmadov on 06.11.2017.
 */

public class Information  extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Shop> shops;
    ListView listView;
    FavAdapter adapter;
    SearchView searchView;
    TextView selsinf;
    TextView text1;
    TextView text2;
    TextView text223;
    TextView text32;
    TextView text322;
    Typeface tnr;
    Typeface tnr2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalesMianFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FavFrag newInstance(String param1, String param2) {
        FavFrag fragment = new FavFrag();
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
        setHasOptionsMenu(true);

        View view =inflater.inflate(R.layout.info, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
     //   toolbar.setTitle("Информация");
        selsinf = (TextView) view.findViewById(R.id.selsinf);
        text1 = (TextView) view.findViewById(R.id.textView2);
        text2 = (TextView) view.findViewById(R.id.textView3);
        text223 = (TextView) view.findViewById(R.id.textView223);
        text32 = (TextView) view.findViewById(R.id.textView32);
        text322 = (TextView) view.findViewById(R.id.textView322);

       /* tnr = Typeface.createFromAsset(getActivity().getAssets(),"11814.ttf");
        tnr2 = Typeface.createFromAsset(getActivity().getAssets(),"11874.ttf");
        selsinf.setTypeface(tnr);
        text1.setTypeface(tnr2);
        text2.setTypeface(tnr2);
        text32.setTypeface(tnr2);
        text322.setTypeface(tnr2);
        text223.setTypeface(tnr2);*/


        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
