package com.akhmadov.saleschr2018;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.akhmadov.saleschr2018.TovarsCategory.TavarsCategories;
import com.akhmadov.saleschr2018.fragments.FavFrag;
import com.akhmadov.saleschr2018.fragments.Information;
import com.akhmadov.saleschr2018.fragments.ShopsCategoriesFragment;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   // SalesMianFrag sales_main;
    ShopsCategoriesFragment shopsCategoriesFragment;
    TavarsCategories tovarsFrag;
    FavFrag favFrag;
    Information information;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Скидки ЧР");
        setSupportActionBar(toolbar);


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        shopsCategoriesFragment = new ShopsCategoriesFragment();
       // sales_main = new SalesMianFrag();
        tovarsFrag = new TavarsCategories();
        favFrag = new FavFrag();
        information = new Information();


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,shopsCategoriesFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_shops) {
            fragmentTransaction.replace(R.id.container,shopsCategoriesFragment);
            toolbar.setTitle("Магазины");

        } else if (id == R.id.nav_slideshow) {
            fragmentTransaction.replace(R.id.container,tovarsFrag);
            toolbar.setTitle("Товары");

        } else if (id == R.id.nav_manage) {
            fragmentTransaction.replace(R.id.container,favFrag);
            toolbar.setTitle("Избранные товары");
        } else if (id == R.id.nav_share) {
            fragmentTransaction.replace(R.id.container,information);
            toolbar.setTitle("Информация");
        }
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
