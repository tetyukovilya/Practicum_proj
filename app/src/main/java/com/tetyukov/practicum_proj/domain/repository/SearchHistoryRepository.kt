package com.tetyukov.practicum_proj.domain.repository

interface SearchHistoryRepository {
    fun addEntry(word: String)
    suspend fun getEntries(): List<String>
    suspend fun clear()
}
