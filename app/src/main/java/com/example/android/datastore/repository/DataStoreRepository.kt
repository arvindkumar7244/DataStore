package com.example.android.datastore.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "my_preference"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class DataStoreRepository(context: Context) {

    private val data = stringPreferencesKey("text")
    suspend fun storeValue(text: String, context: Context) {
        context.dataStore.edit { preferences -> preferences[data] = text }
    }

    val preferenceFlow: Flow<String> = context.dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }
        .map { preferences ->
            preferences[data] ?: "No value"
        }
}
