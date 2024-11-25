package com.lokalook.lokalook.data.remote.response

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define the DataStore for user preferences
val Context.userDataStore: DataStore<Preferences> by preferencesDataStore("userPreferences")

/**
 * Manages user-related preferences in the DataStore.
 */
class UserPreferencesManager private constructor(private val dataStore: DataStore<Preferences>) {

    /**
     * Saves the user's details to the DataStore.
     * @param userName The name of the user.
     * @param userId The unique ID of the user.
     * @param userToken The authentication token for the user.
     */
    suspend fun saveUser(userName: String, userId: String, userToken: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
            preferences[USER_ID_KEY] = userId
            preferences[USER_TOKEN_KEY] = userToken
        }
    }

    /**
     * Retrieves the user's details from the DataStore.
     * @return A Flow containing the user's details.
     */
    fun getUser(): Flow<LoginResult> {
        return dataStore.data.map { preferences ->
            LoginResult(
                name = preferences[USER_NAME_KEY] ?: "",
                userId = preferences[USER_ID_KEY] ?: "",
                token = preferences[USER_TOKEN_KEY] ?: ""
            )
        }
    }

    /**
     * Signs out the user by clearing the stored preferences.
     */
    suspend fun signOut() {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = ""
            preferences[USER_ID_KEY] = ""
            preferences[USER_TOKEN_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var instance: UserPreferencesManager? = null

        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")

        /**
         * Provides the singleton instance of the UserPreferencesManager.
         * @param dataStore The DataStore instance for managing preferences.
         * @return The singleton instance of UserPreferencesManager.
         */
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferencesManager {
            return instance ?: synchronized(this) {
                val newInstance = UserPreferencesManager(dataStore)
                instance = newInstance
                newInstance
            }
        }
    }
}