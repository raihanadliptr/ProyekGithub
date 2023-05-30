package com.example.githubuserapplication.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapplication.ItemsItem
import com.example.githubuserapplication.R
import com.example.githubuserapplication.databinding.ActivityMainBinding
import com.example.githubuserapplication.db.Favorites
import com.example.githubuserapplication.ui.darkmode.DarkModeActivity
import com.example.githubuserapplication.ui.darkmode.DarkModePreferences
import com.example.githubuserapplication.ui.darkmode.DarkModeViewModel
import com.example.githubuserapplication.ui.darkmode.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dark_mode")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.users.observe(this) { users -> setListUserData(users as List<ItemsItem>)}
        mainViewModel.isLoading.observe(this, {pageOnLoading(it)})

        val beforeInstance = DarkModePreferences.getInstance(dataStore)
        val darkModeViewModel = ViewModelProvider(this, ViewModelFactory(beforeInstance)).get( DarkModeViewModel::class.java )
        darkModeViewModel.getTheme().observe(this) { isDark: Boolean ->
            if (!isDark) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dark_mode -> {
                val dark = Intent(this, DarkModeActivity::class.java)
                startActivity(dark)
                return true
            }
            R.id.favorite -> {
                val fav = Intent(this, UserFavoriteActivity::class.java)
                startActivity(fav)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_placeholder)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getUsers(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun pageOnLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setListUserData(users: List<ItemsItem>) {
        val adapter = UsersAdapter(users)
        binding.rvUsers.adapter = adapter
    }
}