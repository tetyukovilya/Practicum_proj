package com.tetyukov.practicum_proj.domain.repository

import com.tetyukov.practicum_proj.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun observePlaylists(): Flow<List<Playlist>>
    fun getPlaylist(playlistId: Long): Flow<Playlist?>
    suspend fun createPlaylist(name: String, description: String)
}
