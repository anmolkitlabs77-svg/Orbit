package com.orbit.other

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class UserData(
    val email: String = "",
    val name: String = "",
    val userId: String = "",
    val isLoggedIn: Boolean = false
)

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")


class dataStore(private val context: Context) {
    private object Keys {
        val EMAIL = stringPreferencesKey("email")
        val NAME = stringPreferencesKey("name")
        val USER_ID = stringPreferencesKey("user_id")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    /** Reactive stream of user data — collect this in Compose with collectAsState() */
    val userDataFlow: Flow<UserData> = context.dataStore.data
        .catch { exception ->
            // DataStore throws IOException on read errors; emit empty prefs instead of crashing
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { prefs ->
            UserData(
                email = prefs[Keys.EMAIL] ?: "",
                name = prefs[Keys.NAME] ?: "",
                userId = prefs[Keys.USER_ID] ?: "",
                isLoggedIn = prefs[Keys.IS_LOGGED_IN] ?: false
            )
        }

    /** One-shot read, e.g. for a splash screen auth check */
    suspend fun getUserOnce(): UserData = userDataFlow.first()

    /** Save/update user info (call after successful login) */
    suspend fun saveUser(email: String, name: String, userId: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.EMAIL] = email
            prefs[Keys.NAME] = name
            prefs[Keys.USER_ID] = userId
            prefs[Keys.IS_LOGGED_IN] = true
        }
    }

    /** Update only specific fields without touching the rest */
    suspend fun updateName(name: String) {
        context.dataStore.edit { prefs -> prefs[Keys.NAME] = name }
    }

    /** Clear all stored user data (call on logout) */
    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}