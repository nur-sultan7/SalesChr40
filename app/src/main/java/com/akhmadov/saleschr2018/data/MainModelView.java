package com.akhmadov.saleschr2018.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainModelView extends AndroidViewModel {
    private static  TovarsDatabase database;

    public MainModelView(@NonNull Application application) {
        super(application);
        database = TovarsDatabase.getInstance(application);
    }

    public void insertFollowingShop(FollowingShop shop)
    {
        new InsertFollowingShop().execute(shop);
    }
    public void deleteFollowingShop(String id)
    {
        new DeleteFollowingShop().execute(id);
    }
    public List<FollowingShop> getAllFollowingShops()
    {
        try {
            return new GetAllFollowingShops().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }
    public List<Integer> getAllFollowingShopsIds()
    {
        try {
            return new GetAllFollowingShopsIds().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public FollowingShop getFollowingShopById (String id)
    {
        try {
            return new GetFollowingShopById().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetFollowingShopById extends AsyncTask<String, Void, FollowingShop>
    {

        @Override
        protected FollowingShop doInBackground(String... integers) {
            FollowingShop followingShop=null;
            if (integers[0]!=null)
                followingShop=database.tovarsDao().getFollowingShopID(integers[0]);
            return followingShop;
        }
    }
    private static class GetAllFollowingShopsIds extends AsyncTask<Void,Void,List<Integer>>
    {

        @Override
        protected List<Integer> doInBackground(Void... voids) {
            return database.tovarsDao().getAllFollowingShopsIds();
        }
    }
    private static class GetAllFollowingShops extends AsyncTask<Void,Void,List<FollowingShop>>
    {

        @Override
        protected List<FollowingShop> doInBackground(Void... voids) {
            return database.tovarsDao().getAllFollowingShops();
        }
    }

    private static class DeleteFollowingShop extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... integers) {
            if (integers[0]!=null)
                database.tovarsDao().deleteFollowingShop(integers[0]);
            return null;
        }
    }

    private static class InsertFollowingShop extends AsyncTask<FollowingShop ,Void, Void>
    {

        @Override
        protected Void doInBackground(FollowingShop... shops) {
            if (shops[0]!=null)
                database.tovarsDao().insertFollowingShop(shops[0]);
            return null;
        }
    }


    public List<FavouriteTovar> getFavouriteTovars(String orderBy)
    {
        try {
            return new GetAllFavouriteTovars().execute(orderBy).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertFavouriteTovar(Tovar tovar)
    {
        new InsertFavouriteTovar().execute(tovar);
    }
    public void deleteFavouriteTovar(String id)
    {
        new DeleteFavouriteTovar().execute(id);
    }
    public FavouriteTovar getFavouriteTovarById(String id){
        try {
            return new GetFavoriteByID().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllFavouriteTovars extends AsyncTask< String,Void, List<FavouriteTovar>>
    {
        @Override
        protected List<FavouriteTovar> doInBackground(String... voids) {
           List<FavouriteTovar> favouriteTovarList=null;
            if (voids[0] !=null) {
                String query ="SELECT * FROM favourite_tovars ORDER BY " + voids[0];
                favouriteTovarList = database.tovarsDao().getAllFavourite(new SimpleSQLiteQuery(query));
            }
            return favouriteTovarList;
        }
    }
    private static class InsertFavouriteTovar extends AsyncTask<Tovar, Void,Void>
    {

        @Override
        protected Void doInBackground(Tovar... tovars) {
            if (tovars[0]!=null) {
                FavouriteTovar favouriteTovar = new FavouriteTovar(tovars[0]);
                database.tovarsDao().insertFavouriteTovar(favouriteTovar);
            }
            return null;
        }
    }

    private static class DeleteFavouriteTovar extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... integers) {
            if (integers[0]!=null)
                database.tovarsDao().deleteFavouriteTovar(integers[0]);
            return null;
        }
    }

   private static class  GetFavoriteByID extends AsyncTask< String, Void, FavouriteTovar>
    {
        @Override
        protected FavouriteTovar doInBackground(String... strings) {
            FavouriteTovar favouriteTovar = null;
            if (strings[0]!=null) {
                String ss= strings[0];
                favouriteTovar = database.tovarsDao().getFavouriteTovarById(ss);
            }
            return favouriteTovar;
        }
    }

}
