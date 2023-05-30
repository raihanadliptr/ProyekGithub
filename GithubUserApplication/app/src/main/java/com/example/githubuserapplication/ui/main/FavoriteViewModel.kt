package com.example.githubuserapplication.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapplication.db.Favorites
import com.example.githubuserapplication.repository.FavoritesRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoritesRepository = FavoritesRepository(application)
    fun getAllFavorite(): LiveData<List<Favorites>> = mFavoriteRepository.getAllFavorite()
}