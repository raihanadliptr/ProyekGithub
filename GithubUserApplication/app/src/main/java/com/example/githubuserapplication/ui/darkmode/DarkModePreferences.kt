package com.example.githubuserapplication.ui.darkmode

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DarkModePreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("dark_mode_theme")

    suspend fun saveTheme(isDark: Boolean) {
        dataStore.edit { preferences -> preferences[THEME_KEY] = isDark }
    }

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[THEME_KEY] ?: false }
    }

    companion object {
        @Volatile
        private var INSTANCE: DarkModePreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): DarkModePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = DarkModePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}