package com.lokalook.lokalook.data.remote.response

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// Define the DataStore for user preferences
val Context.userDataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore("userPreferences")

/**
 * Manages user-related preferences in the DataStore.
 */
class UserPreferencesManager private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveUser(userName: String, userId: String, userToken: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
            preferences[USER_ID_KEY] = userId
            preferences[USER_TOKEN_KEY] = "Bearer $userToken"
        }
    }

    fun getUserToken(): String {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[USER_TOKEN_KEY] ?: ""
            }.first()
        }
    }

    fun getUser(): Flow<LoginResult> {
        return dataStore.data.map { preferences ->
            LoginResult(
                name = preferences[USER_NAME_KEY] ?: "",
                userId = preferences[USER_ID_KEY] ?: "",
                token = preferences[USER_TOKEN_KEY] ?: ""
            )
        }
    }

    companion object {
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")

        @Volatile
        private var instance: UserPreferencesManager? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferencesManager {
            return instance ?: synchronized(this) {
                UserPreferencesManager(dataStore).also { instance = it }
            }
        }
    }
}