package com.example.githubuserapplication.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapplication.ApiConfig
import com.example.githubuserapplication.ItemsItem
import com.example.githubuserapplication.db.Favorites
import com.example.githubuserapplication.repository.FavoritesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isFollowLoading = MutableLiveData<Boolean>()
    val isFollowLoading: LiveData<Boolean> = _isFollowLoading
    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser
    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers
    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following
    private val mFavoriteRepository: FavoritesRepository = FavoritesRepository(application)

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        getUser(UserDetailActivity.username)
        getFollowers(UserDetailActivity.username)
        getFollowing(UserDetailActivity.username)
    }

    fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (!response.isSuccessful) {
                    Log.e(TAG, "onFailure: ${response.message()}")
                } else {
                    _detailUser.value = response.body()
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isFollowLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isFollowLoading.value = false
                if (!response.isSuccessful) {
                    Log.e(TAG, "onFailure: ${response.message()}")
                } else {
                    _following.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isFollowLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isFollowLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isFollowLoading.value = false
                if (!response.isSuccessful) {
                    Log.e(TAG, "onFailure: ${response.message()}")
                } else {
                    _followers.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isFollowLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFavorite(username: String): LiveData<Favorites> = mFavoriteRepository.getFavoriteByUsername(username)

    fun addFavorite(favorite: Favorites) {
        mFavoriteRepository.addFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorites) {
        mFavoriteRepository.deleteFavorite(favorite)
    }

}