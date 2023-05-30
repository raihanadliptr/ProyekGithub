package com.example.githubuserapplication.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapplication.db.Favorites
import com.example.githubuserapplication.db.FavoritesDao
import com.example.githubuserapplication.db.FavoritesRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoritesRepository(application: Application) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val mFavoriteUserDao: FavoritesDao

    init {
        val database = FavoritesRoomDatabase.getDatabase(application)
        mFavoriteUserDao = database.favoritesDao()
    }

    fun getAllFavorite() : LiveData<List<Favorites>> = mFavoriteUserDao.getAllFavoriteUser()

    fun getFavoriteByUsername(username : String): LiveData<Favorites> = mFavoriteUserDao.getFavoriteByUsername(username)

    fun addFavorite(favorite: Favorites) {
        executorService.execute {
            mFavoriteUserDao.addFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorites) {
        executorService.execute {
            mFavoriteUserDao.deleteFavorite(favorite)
        }
    }
}