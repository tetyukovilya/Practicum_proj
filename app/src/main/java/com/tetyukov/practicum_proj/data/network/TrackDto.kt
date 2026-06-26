package com.tetyukov.practicum_proj.data.network

data class TrackDto(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int
)