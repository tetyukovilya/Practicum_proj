package com.tetyukov.practicum_proj.data.db.converters

import androidx.room.TypeConverter

class PlaylistIdsConverter {
    @TypeConverter
    fun fromPlaylistIds(value: Set<Long>):
            String = value.joinToString(",")

    @TypeConverter
    fun toPlaylistIds(value: String): Set<Long> {
        if (value.isBlank()) return emptySet()
        return value.split(",")
            .mapNotNull { it.toLongOrNull() }
            .toSet()
    }
}