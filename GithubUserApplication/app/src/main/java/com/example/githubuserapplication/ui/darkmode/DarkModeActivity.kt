package com.example.githubuserapplication.ui.darkmode

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapplication.R
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dark_mode")

class DarkModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        val changeCurrentTheme = findViewById<SwitchMaterial>(R.id.change_themes)
        val beforeInstance = DarkModePreferences.getInstance(dataStore)
        val darkModeViewModel = ViewModelProvider(this, ViewModelFactory(beforeInstance)).get(DarkModeViewModel::class.java)

        darkModeViewModel.getTheme().observe(this) { isDark : Boolean ->
            if (!isDark) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            changeCurrentTheme.isChecked = if (!isDark) false else true
        }
        changeCurrentTheme.setOnCheckedChangeListener { _: CompoundButton?, validate: Boolean -> darkModeViewModel.saveTheme(validate) }
    }
}