package com.tetyukov.practicum_proj.data.repo

import com.tetyukov.practicum_proj.data.preferences.SearchHistoryPreferences
import com.tetyukov.practicum_proj.domain.repository.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {
    override fun addEntry(word: String) {
        preferences.addEntry(word)
    }

    override suspend fun getEntries(): List<String> = preferences.getEntries()

    override suspend fun clear() {
        preferences.clear()
    }
}
