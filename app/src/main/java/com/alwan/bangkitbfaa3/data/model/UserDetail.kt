package com.alwan.bangkitbfaa3.data.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    val login: String? = null,
    val name: String? = null,
    val type: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val company: String? = null,
    val location: String? = null,
    @SerializedName("public_repos")
    val publicRepos: String? = null
)