package com.akhmadov.saleschr2018.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TovarsDao {

    @Insert
    void insertFavouriteTovar(FavouriteTovar tovar);

    @Query( "Delete from favourite_tovars where id_tovar LIKE :id" )
    void deleteFavouriteTovar(String id);

   @Query("SELECT * FROM favourite_tovars WHERE id_tovar LIKE :id_tovar")
    FavouriteTovar getFavouriteTovarById(String id_tovar);

    @Query("Select * from favourite_tovars")
    List<FavouriteTovar> getAllFavourite();


}
