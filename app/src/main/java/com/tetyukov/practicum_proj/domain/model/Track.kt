package com.tetyukov.practicum_proj.domain.model

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String? = null,
    val isFavorite: Boolean = false,
    val playlistIds: Set<Long> = emptySet()
)
