package com.tetyukov.practicum_proj.domain

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}