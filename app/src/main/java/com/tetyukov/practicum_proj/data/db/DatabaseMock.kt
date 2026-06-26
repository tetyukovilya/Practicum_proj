package com.tetyukov.practicum_proj.data.db

import com.tetyukov.practicum_proj.domain.model.Playlist
import com.tetyukov.practicum_proj.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DatabaseMock {
    private val playlists = MutableStateFlow<List<Playlist>>(emptyList())
    private val tracks = MutableStateFlow<List<Track>>(emptyList())
    private var playlistIdCounter = 1L

    fun observePlaylists(): Flow<List<Playlist>> {
        return combine(playlists, tracks) { playlistList, trackList ->
            playlistList.map { playlist ->
                playlist.copy(
                    tracks = trackList.filter { track -> playlist.id in track.playlistIds }
                )
            }
        }
    }

    fun observePlaylist(playlistId: Long): Flow<Playlist?> {
        return observePlaylists().map { list -> list.find { it.id == playlistId } }
    }

    fun observeFavoriteTracks(): Flow<List<Track>> = tracks.map { list ->
        list.filter { it.isFavorite }
    }

    fun observeTrack(trackId: Long): Flow<Track?> = tracks.map { list ->
        list.find { it.id == trackId }
    }

    fun getTrack(trackId: Long): Track? = tracks.value.find { it.id == trackId }

    fun upsertTrack(track: Track) {
        tracks.value = tracks.value
            .filterNot { it.id == track.id }
            .plus(track)
            .sortedBy { it.id }
    }

    fun createPlaylist(name: String, description: String) {
        val newPlaylist = Playlist(
            id = playlistIdCounter++,
            name = name,
            description = description
        )
        playlists.value = playlists.value + newPlaylist
    }

    fun addTrackToPlaylist(trackId: Long, playlistId: Long) {
        val track = getTrack(trackId) ?: return
        upsertTrack(track.copy(playlistIds = track.playlistIds + playlistId))
    }

    fun setFavorite(trackId: Long, isFavorite: Boolean) {
        val track = getTrack(trackId) ?: return
        upsertTrack(track.copy(isFavorite = isFavorite))
    }
}