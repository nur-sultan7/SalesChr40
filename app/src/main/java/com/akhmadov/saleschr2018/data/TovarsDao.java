package com.akhmadov.saleschr2018.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TovarsDao {

    @Insert
    void insertFavouriteTovar(Tovar tovar);

    @Query( "Delete from favourite_tovars where uniqueId==:id" )
    void deleteFavouriteTovar(int id);

    @Query("Select * from favourite_tovars")
    List<FavouriteTovar> getAllFavourite();


}
