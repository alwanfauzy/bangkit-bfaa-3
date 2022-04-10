package com.alwan.bangkitbfaa3.util

import android.widget.ImageView
import com.alwan.bangkitbfaa3.R
import com.alwan.bangkitbfaa3.data.model.User
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.avatar_placeholder)
        .into(this)
}

fun List<User>.toArrayList(): ArrayList<User> {
    val listUser = ArrayList<User>()
    for (user in this) {
        val userMapped = User(
            user.id,
            user.login,
            user.type,
            user.avatarUrl,
        )
        listUser.add(userMapped)
    }
    return listUser
}