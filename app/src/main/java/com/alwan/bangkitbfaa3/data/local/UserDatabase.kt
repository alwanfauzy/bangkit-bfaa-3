package com.alwan.bangkitbfaa3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alwan.bangkitbfaa3.data.model.User

@Database(
    entities = [User::class],
    version = 1
)

abstract class UserDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteUserDao(): FavoriteUserDao
}