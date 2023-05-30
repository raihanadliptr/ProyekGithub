package com.example.githubuserapplication.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapplication.ItemsItem
import com.example.githubuserapplication.databinding.ActivityUserFavoriteBinding

class UserFavoriteActivity : AppCompatActivity() {
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){ ViewModelFactory.getInstance(application) }
    private var binding: ActivityUserFavoriteBinding? = null
    private val favoritesActivityBinding get() = binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(favoritesActivityBinding?.root)

        val layoutManager = LinearLayoutManager(this)
        favoritesActivityBinding?.rvFavorite?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        favoritesActivityBinding?.rvFavorite?.addItemDecoration(itemDecoration)

        favoriteViewModel.getAllFavorite().observe(this) { favorites ->
            val items = arrayListOf<ItemsItem>()
            favorites!!.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            favoritesActivityBinding?.rvFavorite?.adapter = UsersAdapter(items)
        }
    }
}