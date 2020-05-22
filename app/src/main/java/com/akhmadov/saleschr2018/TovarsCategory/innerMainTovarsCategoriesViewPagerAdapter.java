package com.akhmadov.saleschr2018.TovarsCategory;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class innerMainTovarsCategoriesViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments=new ArrayList<>();

    public innerMainTovarsCategoriesViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //ADD PAGE
    public void addFragment(Fragment f)
    {
        fragments.add(f);
    }

    //set title

    @Override
    public CharSequence getPageTitle(int position) {
        if (getCount()>1)
        {
        String title=fragments.get(position).toString();
        return title;}
        else return "";
    }
}