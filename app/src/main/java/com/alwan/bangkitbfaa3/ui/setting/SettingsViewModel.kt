package com.alwan.bangkitbfaa3.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alwan.bangkitbfaa3.util.SettingsPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingsPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}