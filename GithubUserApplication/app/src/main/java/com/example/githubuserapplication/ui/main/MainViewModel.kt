package com.example.githubuserapplication.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapplication.ApiConfig
import com.example.githubuserapplication.GithubUserResponse
import com.example.githubuserapplication.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _users = MutableLiveData<List<ItemsItem?>?>()
    val users: LiveData<List<ItemsItem?>?> = _users

    companion object{
        private const val USERNAME = "raihan"
        private const val TAG = "MainViewModel"
    }

    init{
        getUsers(USERNAME)
    }

    fun getUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserByUsername(username)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                _isLoading.value = false
                if (!response.isSuccessful) {
                    Log.e(TAG, "onFailure: ${response.message()}")
                } else {
                    _users.value = response.body()?.items
                }
            }
            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}