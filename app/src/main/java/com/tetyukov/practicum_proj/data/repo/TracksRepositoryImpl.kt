package com.tetyukov.practicum_proj.data.repo

import com.tetyukov.practicum_proj.storage.Storage
import com.tetyukov.practicum_proj.data.db.AppDatabase
import com.tetyukov.practicum_proj.data.db.entity.TrackEntity
import com.tetyukov.practicum_proj.domain.model.Track
import com.tetyukov.practicum_proj.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val storage: Storage,
    database: AppDatabase
) : TracksRepository {
    private val trackDao = database.trackDao()

    override suspend fun searchTracks(expression: String): List<Track> {
        val tracksFromSource = storage.searchTracks(expression)
        val persistedTracks = tracksFromSource.map { track ->
            val existingTrack = trackDao.getTrack(track.id)
            track.copy(
                isFavorite = existingTrack?.isFavorite ?: false,
                playlistIds = existingTrack?.playlistIds ?: emptySet()
            )
        }
        trackDao.upsertTracks(persistedTracks.map { it.toEntity() })
        return persistedTracks
    }

    override fun observeFavoriteTracks(): Flow<List<Track>> {
        return trackDao.observeFavoriteTracks().map { tracks -> tracks.map { it.toDomain() } }
    }

    override fun observeTrack(trackId: Long): Flow<Track?> {
        return trackDao.observeTrack(trackId).map { it?.toDomain() }
    }

    override suspend fun setFavorite(trackId: Long, isFavorite: Boolean) {
        val track = trackDao.getTrack(trackId) ?: return
        trackDao.updateTrack(track.copy(isFavorite = isFavorite))
    }

    override suspend fun addTrackToPlaylist(trackId: Long, playlistId: Long) {
        val track = trackDao.getTrack(trackId) ?: return
        trackDao.updateTrack(track.copy(playlistIds = track.playlistIds + playlistId))
    }

    private fun TrackEntity.toDomain(): Track {
        return Track(
            id = id,
            trackName = trackName,
            artistName = artistName,
            trackTime = trackTime,
            isFavorite = isFavorite,
            playlistIds = playlistIds
        )
    }

    private fun Track.toEntity(): TrackEntity {
        return TrackEntity(
            id = id,
            trackName = trackName,
            artistName = artistName,
            trackTime = trackTime,
            isFavorite = isFavorite,
            playlistIds = playlistIds
        )
    }
}