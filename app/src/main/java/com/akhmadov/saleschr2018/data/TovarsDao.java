package com.akhmadov.saleschr2018.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface TovarsDao {

    @Insert
    void insertFavouriteTovar(FavouriteTovar tovar);

    @Query( "Delete from favourite_tovars where id_tovar LIKE :id" )
    void deleteFavouriteTovar(String id);

   @Query("SELECT * FROM favourite_tovars WHERE id_tovar LIKE :id_tovar")
    FavouriteTovar getFavouriteTovarById(String id_tovar);

    /*@Query("Select * from favourite_tovars order by new_cena desc")
    List<FavouriteTovar> getAllFavourite(String orderBy);*/

    @RawQuery
    List<FavouriteTovar> getAllFavourite(SupportSQLiteQuery sortQuery);


}
