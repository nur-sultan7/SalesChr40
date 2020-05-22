package com.akhmadov.saleschr2018.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainModelView extends AndroidViewModel {
    private static  TovarsDatabase database;

    public MainModelView(@NonNull Application application) {
        super(application);
        database = TovarsDatabase.getInstance(application);
    }

    public List<FavouriteTovar> getFavouriteTovars()
    {
        try {
            return new GetAllFavouriteTovars().execute().get();
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
    public void deleteFavouriteTovar(Integer id)
    {
        new DeleteFavouriteTovar().execute(id);
    }

    private static class GetAllFavouriteTovars extends AsyncTask<Void,Void, List<FavouriteTovar>>
    {
        @Override
        protected List<FavouriteTovar> doInBackground(Void... voids) {
            List<FavouriteTovar> favouriteTovarList = database.tovarsDao().getAllFavourite();
            return favouriteTovarList;
        }
    }
    private static class InsertFavouriteTovar extends AsyncTask<Tovar, Void,Void>
    {

        @Override
        protected Void doInBackground(Tovar... tovars) {
            if (tovars[0]!=null)
                database.tovarsDao().insertFavouriteTovar(tovars[0]);
            return null;
        }
    }

    private static class DeleteFavouriteTovar extends AsyncTask<Integer, Void, Void>
    {

        @Override
        protected Void doInBackground(Integer... integers) {
            if (integers[0]!=null)
                database.tovarsDao().deleteFavouriteTovar(integers[0]);
            return null;
        }
    }

}
