package com.tetyukov.practicum_proj.creator

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.tetyukov.practicum_proj.data.db.AppDatabase
import com.tetyukov.practicum_proj.data.preferences.SearchHistoryPreferences
import com.tetyukov.practicum_proj.data.repo.PlaylistsRepositoryImpl
import com.tetyukov.practicum_proj.data.repo.SearchHistoryRepositoryImpl
import com.tetyukov.practicum_proj.data.repo.TracksRepositoryImpl
import com.tetyukov.practicum_proj.storage.Storage
import com.tetyukov.practicum_proj.domain.repository.PlaylistsRepository
import com.tetyukov.practicum_proj.domain.repository.SearchHistoryRepository
import com.tetyukov.practicum_proj.domain.repository.TracksRepository

private val Context.searchHistoryDataStore by preferencesDataStore(name = "search_history_preferences")

object Creator {
    @Volatile
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun requireContext(): Context {
        return checkNotNull(appContext) { "Creator is not initialized. Call Creator.init(context) first." }
    }

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "playlist_maker_database"
        ).build()
    }

    private val storage by lazy { Storage() }
    private val searchHistoryPreferences by lazy { SearchHistoryPreferences(requireContext().searchHistoryDataStore) }
    private val tracksRepository by lazy { TracksRepositoryImpl(storage = storage, database = database) }
    private val playlistsRepository by lazy { PlaylistsRepositoryImpl(database = database) }
    private val searchHistoryRepository by lazy { SearchHistoryRepositoryImpl(searchHistoryPreferences) }

    fun getTracksRepository(): TracksRepository = tracksRepository
    fun getPlaylistsRepository(): PlaylistsRepository = playlistsRepository
    fun getSearchHistoryRepository(): SearchHistoryRepository = searchHistoryRepository
}