package com.tetyukov.practicum_proj.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + CoroutineName("search-history-preferences") + SupervisorJob()
    )
) {
    fun addEntry(word: String) {
        val normalized = word.trim()
        if (normalized.isEmpty()) return

        coroutineScope.launch {
            dataStore.edit { preferences ->
                val current = preferences[preferencesKey]
                    .orEmpty()
                    .takeIf { it.isNotBlank() }
                    ?.split(SEPARATOR)
                    ?.toMutableList()
                    ?: mutableListOf()

                current.remove(normalized)
                current.add(0, normalized)

                val updated = current.take(MAX_ENTRIES).joinToString(SEPARATOR)
                preferences[preferencesKey] = updated
            }
        }
    }

    suspend fun getEntries(): List<String> {
        return dataStore.data
            .map { preferences ->
                preferences[preferencesKey]
                    .orEmpty()
                    .takeIf { it.isNotBlank() }
                    ?.split(SEPARATOR)
                    ?.filter { it.isNotBlank() }
                    ?: emptyList()
            }
            .first()
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }

    private companion object {
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = "||"
        private val preferencesKey = stringPreferencesKey("search_history")
    }
}