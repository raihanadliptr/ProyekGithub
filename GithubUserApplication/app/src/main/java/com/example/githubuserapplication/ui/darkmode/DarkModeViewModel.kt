package com.example.githubuserapplication.ui.darkmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DarkModeViewModel (private val beforeInstance: DarkModePreferences) : ViewModel() {
    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            beforeInstance.saveTheme(isDark)
        }
    }

    fun getTheme(): LiveData<Boolean> {
        return beforeInstance.getTheme().asLiveData()
    }
}