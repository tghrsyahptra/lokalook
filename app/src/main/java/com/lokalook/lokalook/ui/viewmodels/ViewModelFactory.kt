package com.lokalook.lokalook.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lokalook.lokalook.data.remote.EventRepository
import com.lokalook.lokalook.di.Injection
import com.lokalook.lokalook.ui.SettingPreferences
import com.lokalook.lokalook.ui.dataStore

class ViewModelFactory(
    private val eventRepository: EventRepository,
    private val settingPreferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Memeriksa apakah modelClass adalah MainViewModel
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(eventRepository, settingPreferences) as T
        }
        throw IllegalArgumentException("Kelas ViewModel tidak dikenal: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        // Mengambil instance ViewModelFactory
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                val repository = Injection.provideRepository(context)
                val preferences = SettingPreferences.getInstance(context.dataStore)
                instance ?: ViewModelFactory(repository, preferences)
            }.also { instance = it }
    }
}