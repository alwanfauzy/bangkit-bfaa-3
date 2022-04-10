package com.alwan.bangkitbfaa3.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val login: String? = null,
    val type: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null
) : Parcelable