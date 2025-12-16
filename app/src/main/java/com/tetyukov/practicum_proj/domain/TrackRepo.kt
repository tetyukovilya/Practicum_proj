package com.tetyukov.practicum_proj.domain

import com.tetyukov.practicum_proj.domain.TrackDto

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<TrackDto>
}