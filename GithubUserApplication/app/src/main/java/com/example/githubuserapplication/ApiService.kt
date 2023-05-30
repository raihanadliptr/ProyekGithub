package com.example.githubuserapplication

import com.example.githubuserapplication.ui.main.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUserByUsername(
        @Query("q") username: String
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}