package com.geko.challenge.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppDatastore @Inject constructor(
    private val store: DataStore<Preferences>,
) {
    private val isUserLoggedInKey = booleanPreferencesKey("is_logged_in")

    val isUserLoggedIn = store.data
        .map { preferences ->
            // No type safety.
            preferences[isUserLoggedInKey] ?: false
        }

    suspend fun setIsUserLoggedIn(isLoggedIn : Boolean) {
        try {
            store.edit { settings ->
                settings[isUserLoggedInKey] = isLoggedIn
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }
}