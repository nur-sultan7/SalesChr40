package com.akhmadov.saleschr2018.TovarsCategory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.akhmadov.saleschr2018.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class InnerMainTovarsCategories extends Fragment implements TabLayout.OnTabSelectedListener,ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private TabLayout tabLayout;
    private ArrayList<String> tabs;
    private String cat_name;
    private String cat_id;

    static Fragment newInstance(String cat_id,String cat_name, ArrayList<String> array)
    {
        Fragment tovarsFrag = new InnerMainTovarsCategories();
        Bundle bundle = new Bundle();
        bundle.putString("cat_id", cat_id);
        bundle.putString("cat_name",cat_name);
        bundle.putStringArrayList("tabs", array);
        tovarsFrag.setArguments(bundle);
        return tovarsFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabs= getArguments() != null ? getArguments().getStringArrayList("tabs") : null;
        cat_id=getArguments().getString("cat_id");
        cat_name=getArguments().getString("cat_name");

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tovars_categories_main_inner,container,false);
        Toolbar toolbar =  getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(cat_name);
        vp=  view.findViewById(R.id.tovars_categories_main_viewpager);
        tabLayout=  view.findViewById(R.id.tovars_categories_main_tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        this.addPages();
        tabLayout.setupWithViewPager(vp);
      //  tabLayout.addOnTabSelectedListener();
        return view;
    }
    private void addPages()
    {
        innerMainTovarsCategoriesViewPagerAdapter pagerAdapter=new innerMainTovarsCategoriesViewPagerAdapter(getChildFragmentManager());
        if (tabs!=null)
            for (String tab: tabs) {
                tabLayout.setVisibility(View.VISIBLE);
                pagerAdapter.addFragment(InnerTovarsCategories.newInstance(cat_id,tab));
            }
        else {
            tabLayout.setVisibility(View.GONE);
            pagerAdapter.addFragment(InnerTovarsCategories.newInstance(cat_id,null));
        }



        vp.setAdapter(pagerAdapter);
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
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
