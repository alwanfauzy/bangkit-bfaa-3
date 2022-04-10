package com.alwan.bangkitbfaa3.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alwan.bangkitbfaa3.data.model.SearchResponse
import com.alwan.bangkitbfaa3.data.model.User
import com.alwan.bangkitbfaa3.data.remote.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _users = MutableLiveData<ArrayList<User>>()
    val users: LiveData<ArrayList<User>> = _users

    fun searchUsers(query: String) {
        val client = RetrofitConfig.apiInstance.getSearchUsers(query)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _users.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure searchUsers ${t.message}")
            }
        })
    }
}