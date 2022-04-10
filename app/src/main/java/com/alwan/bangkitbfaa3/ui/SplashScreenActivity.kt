package com.alwan.bangkitbfaa3.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.alwan.bangkitbfaa3.databinding.ActivitySplashScreenBinding
import com.alwan.bangkitbfaa3.ui.main.MainActivity
import com.alwan.bangkitbfaa3.ui.setting.SettingsViewModel
import com.alwan.bangkitbfaa3.util.SettingsPreferences
import com.alwan.bangkitbfaa3.util.ViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDarkTheme()
        Handler(Looper.getMainLooper()).postDelayed( {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupDarkTheme() {
        val pref = SettingsPreferences.getInstance(dataStore)
        settingsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[SettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    companion object {
        const val SPLASH_DELAY = 1000L
    }
}