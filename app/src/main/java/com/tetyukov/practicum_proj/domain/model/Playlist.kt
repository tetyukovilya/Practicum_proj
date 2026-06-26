package com.tetyukov.practicum_proj.domain.model

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val tracks: List<Track> = emptyList()
)
