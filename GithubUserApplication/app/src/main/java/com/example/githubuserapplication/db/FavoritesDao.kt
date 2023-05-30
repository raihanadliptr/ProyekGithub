package com.example.githubuserapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favorite: Favorites)

    @Query("SELECT * FROM Favorites WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<Favorites>

    @Delete
    fun deleteFavorite(favorite: Favorites)

    @Query("SELECT * FROM Favorites")
    fun getAllFavoriteUser(): LiveData<List<Favorites>>
}