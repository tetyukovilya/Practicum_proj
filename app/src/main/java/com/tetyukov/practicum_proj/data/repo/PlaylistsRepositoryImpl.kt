package com.tetyukov.practicum_proj.data.repo

import com.tetyukov.practicum_proj.data.db.AppDatabase
import com.tetyukov.practicum_proj.data.db.entity.PlaylistEntity
import com.tetyukov.practicum_proj.data.db.entity.TrackEntity
import com.tetyukov.practicum_proj.domain.model.Playlist
import com.tetyukov.practicum_proj.domain.model.Track
import com.tetyukov.practicum_proj.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PlaylistsRepositoryImpl(
    database: AppDatabase
) : PlaylistsRepository {

    private val playlistDao = database.playlistDao()
    private val trackDao = database.trackDao()

    override fun observePlaylists(): Flow<List<Playlist>> {
        return combine(
            playlistDao.observePlaylists(),
            trackDao.observeAllTracks()
        ) { playlistEntities, trackEntities ->
            playlistEntities.map { playlistEntity ->
                val playlistTracks = trackEntities
                    .filter { trackEntity -> playlistEntity.id in trackEntity.playlistIds }
                    .map { trackEntity -> trackEntity.toDomain() }

                playlistEntity.toDomain(playlistTracks)
            }
        }
    }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return combine(
            playlistDao.observePlaylist(playlistId),
            trackDao.observeAllTracks()
        ) { playlistEntity, trackEntities ->
            playlistEntity?.let { entity ->
                val playlistTracks = trackEntities
                    .filter { trackEntity -> entity.id in trackEntity.playlistIds }
                    .map { trackEntity -> trackEntity.toDomain() }

                entity.toDomain(playlistTracks)
            }
        }
    }

    override suspend fun createPlaylist(name: String, description: String) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description
            )
        )
    }

    private fun PlaylistEntity.toDomain(tracks: List<Track>): Playlist {
        return Playlist(
            id = id,
            name = name,
            description = description,
            tracks = tracks
        )
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
}