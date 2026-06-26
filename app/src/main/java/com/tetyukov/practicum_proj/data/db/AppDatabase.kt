package com.tetyukov.practicum_proj.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tetyukov.practicum_proj.data.db.converters.PlaylistIdsConverter
import com.tetyukov.practicum_proj.data.db.dao.PlaylistDao
import com.tetyukov.practicum_proj.data.db.dao.TrackDao
import com.tetyukov.practicum_proj.data.db.entity.PlaylistEntity
import com.tetyukov.practicum_proj.data.db.entity.TrackEntity

@Database(
    entities = [TrackEntity::class, PlaylistEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PlaylistIdsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
}
