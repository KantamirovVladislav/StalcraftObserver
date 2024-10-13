package com.example.stalcraftobserver.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.stalcraftobserver.domain.manager.LocalUserManager
import com.example.stalcraftobserver.util.Constants
import com.example.stalcraftobserver.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerRel(
    private val context: Context
) : LocalUserManager {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }

    override suspend fun saveUserRegion(region: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.USER_REGION] = region
        }
    }

    override fun readUserRegion(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_REGION] ?: "ru"
        }
    }

}

//Init dataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

//Init dataStore keys
private object PreferencesKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
    val USER_REGION = stringPreferencesKey(name = Constants.USER_REGION)
}