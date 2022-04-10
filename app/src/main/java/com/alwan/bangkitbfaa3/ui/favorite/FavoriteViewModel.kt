package com.alwan.bangkitbfaa3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alwan.bangkitbfaa3.data.local.FavoriteUserDao
import com.alwan.bangkitbfaa3.data.local.UserDatabase
import com.alwan.bangkitbfaa3.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteUserDao? = null
    private var userDB: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDB?.favoriteUserDao()
    }

    fun getFavoriteUsers(): LiveData<List<User>>? {
        return userDao?.getFavoriteUser()
    }

    fun addToFavorite(data: User) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(
                data.id,
                data.login,
                data.type,
                data.avatarUrl,
            )
            userDao?.addToFavorite(user)
        }
    }

    fun removeFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUserFavorites(id)
        }
    }

    fun isFavoriteUser(id: Int) = userDao?.isFavoriteUser(id)
}