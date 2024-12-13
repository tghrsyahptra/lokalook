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

// Extension property to create the DataStore instance
val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")

/**
 * Manages user-related preferences using DataStore.
 */
class UserPreferencesManager private constructor(private val dataStore: DataStore<Preferences>) {

    /**
     * Saves user details into DataStore.
     * @param userName Name of the user.
     * @param userId ID of the user.
     * @param userToken Authentication token of the user.
     */
    suspend fun saveUser( userId: String, userToken: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USER_TOKEN_KEY] = "Bearer $userToken"
        }
    }

    /**
     * Retrieves the user authentication token.
     * @return The user's token, or an empty string if not available.
     */
    fun getUserToken(): String {
        return runBlocking {
            dataStore.data
                .map { preferences -> preferences[USER_TOKEN_KEY] ?: "" }
                .first()
        }
    }

    /**
     * Retrieves user information as a Flow object.
     * @return A [Flow] emitting [LoginResult] with user details.
     */
    fun getUser(): Flow<LoginResult> {
        return dataStore.data.map { preferences ->
            LoginResult(
                userId = preferences[USER_ID_KEY] ?: "",
                token = preferences[USER_TOKEN_KEY] ?: ""
            )
        }
    }

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")

        @Volatile
        private var instance: UserPreferencesManager? = null

        /**
         * Provides a singleton instance of [UserPreferencesManager].
         * @param dataStore The DataStore instance.
         */
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferencesManager {
            return instance ?: synchronized(this) {
                UserPreferencesManager(dataStore).also { instance = it }
            }
        }
    }
}