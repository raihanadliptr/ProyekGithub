package com.example.githubuserapplication.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapplication.R
import com.example.githubuserapplication.databinding.ActivityUserDetailBinding
import com.example.githubuserapplication.db.Favorites
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private var binding: ActivityUserDetailBinding? = null
    private val detailActivityBinding get() = binding
    private var favorites: Boolean = false
    private val detailViewModel by viewModels<DetailViewModel>(){ ViewModelFactory.getInstance(application) }

    companion object {
        const val username = "username"
        const val avatarURL = "url"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(detailActivityBinding?.root)

        val username = intent.getStringExtra(username)
        val avatarUrl = intent.getStringExtra(avatarURL)
        if (username != null) {
            detailViewModel.getUser(username)
        }
        detailViewModel.detailUser.observe(this){details -> dataUser(details)}
        detailViewModel.isLoading.observe(this, {pageOnLoading(it)})

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.username = username
        }
        val viewPager: ViewPager2 = detailActivityBinding!!.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailActivityBinding!!.tabs
        TabLayoutMediator(tabs, viewPager) { tab, pos -> tab.text = resources.getString(TAB_TITLES[pos])}.attach()
        supportActionBar?.elevation = 0f

        val favorite = detailActivityBinding!!.fabAdd
        var validate: Favorites? = null

        detailViewModel.getFavorite(username!!).observe(this) {
            validate = it
            if (validate != null) {
                favorite.setImageDrawable(ContextCompat.getDrawable(favorite.context, R.drawable.ic_favorite_24))
                favorites = true
                favorite.setOnClickListener {
                    detailViewModel.deleteFavorite(validate as Favorites)
                    Toast.makeText(this, username + " is no longer your favorites!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                favorite.setImageDrawable(ContextCompat.getDrawable(favorite.context, R.drawable.ic_favorite_border_24))
                favorite.setOnClickListener {
                    detailViewModel.addFavorite(Favorites(username!!, avatarUrl) as Favorites)
                    Toast.makeText(this, username + " is your favorite now!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dataUser(details: UserDetailResponse) {
        detailActivityBinding!!.tvUsername.text = details.login
        detailActivityBinding!!.tvUserName.text = details.name
        detailActivityBinding!!.tvFollowing.text = "${details.following.toString()} Following"
        detailActivityBinding!!.tvFollower.text = "${details.followers.toString()} Followers"
        Glide.with(detailActivityBinding!!.root).load(details.avatarUrl).into(detailActivityBinding!!.itemImage)
    }

    private fun pageOnLoading(loading: Boolean) {
        if (loading) {
            detailActivityBinding!!.tvUsername.visibility = View.GONE
            detailActivityBinding!!.tvUserName.visibility = View.GONE
            detailActivityBinding!!.tvFollowing.visibility = View.GONE
            detailActivityBinding!!.tvFollower.visibility = View.GONE
            detailActivityBinding!!.itemImage.visibility = View.GONE
            detailActivityBinding!!.progressBar.visibility = View.VISIBLE
        }
        else {
            detailActivityBinding!!.tvUsername.visibility = View.VISIBLE
            detailActivityBinding!!.tvUserName.visibility = View.VISIBLE
            detailActivityBinding!!.tvFollowing.visibility = View.VISIBLE
            detailActivityBinding!!.tvFollower.visibility = View.VISIBLE
            detailActivityBinding!!.itemImage.visibility = View.VISIBLE
            detailActivityBinding!!.progressBar.visibility = View.GONE
        }
    }

    private fun getViewModel(activity: AppCompatActivity): DetailViewModel {
        val viewModelFactory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}