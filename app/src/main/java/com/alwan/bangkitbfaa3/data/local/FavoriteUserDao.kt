package com.alwan.bangkitbfaa3.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alwan.bangkitbfaa3.data.model.User

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favoriteUserDao: User)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<User>>

    @Query(" SELECT COUNT(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun isFavoriteUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id ")
    fun removeUserFavorites(id: Int): Int

    @Query("SELECT * FROM favorite_user")
    fun findAll(): Cursor
}