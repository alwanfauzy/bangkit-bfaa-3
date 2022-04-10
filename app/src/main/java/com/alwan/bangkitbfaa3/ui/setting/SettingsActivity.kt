package com.alwan.bangkitbfaa3.ui.setting

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.alwan.bangkitbfaa3.databinding.ActivitySettingsBinding
import com.alwan.bangkitbfaa3.util.SettingsPreferences
import com.alwan.bangkitbfaa3.util.ViewModelFactory

class SettingsActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener{
    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsViewModel: SettingsViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingsPreferences.getInstance(dataStore)
        settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCheckedChanged(cb: CompoundButton?, isChecked: Boolean) {
        settingsViewModel.saveThemeSetting(isChecked)
    }
}