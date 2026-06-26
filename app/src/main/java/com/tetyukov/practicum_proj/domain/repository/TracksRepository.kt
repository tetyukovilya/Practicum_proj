package com.tetyukov.practicum_proj.domain.repository

import com.tetyukov.practicum_proj.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    fun observeFavoriteTracks(): Flow<List<Track>>
    fun observeTrack(trackId: Long): Flow<Track?>
    suspend fun setFavorite(trackId: Long, isFavorite: Boolean)
    suspend fun addTrackToPlaylist(trackId: Long, playlistId: Long)
}