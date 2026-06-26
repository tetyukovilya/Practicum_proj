package com.tetyukov.practicum_proj.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val isFavorite: Boolean = false,
    val playlistIds: Set<Long> = emptySet()
)
