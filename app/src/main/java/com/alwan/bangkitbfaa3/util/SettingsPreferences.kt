package com.alwan.bangkitbfaa3.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val theme = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[theme] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[theme] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}