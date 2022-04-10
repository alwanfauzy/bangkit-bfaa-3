package com.alwan.bangkitbfaa3.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alwan.bangkitbfaa3.ui.setting.SettingsViewModel

class ViewModelFactory(private val pref: SettingsPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}