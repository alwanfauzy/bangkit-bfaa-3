package com.alwan.bangkitbfaa3.data.remote

import com.alwan.bangkitbfaa3.data.model.SearchResponse
import com.alwan.bangkitbfaa3.data.model.User
import com.alwan.bangkitbfaa3.data.model.UserDetail
import com.alwan.bangkitbfaa3.util.NetworkInfo.GITHUB_TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getDetailUsers(
        @Path("username") username: String,
    ): Call<UserDetail>

    @GET("users/{username}/following")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getFollowingUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: $GITHUB_TOKEN")
    fun getFollowersUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}