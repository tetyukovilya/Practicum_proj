package com.tetyukov.practicum_proj.storage

import com.tetyukov.practicum_proj.domain.model.Track

class Storage {
    private val listTracks = listOf(
        Track(id = 1, trackName = "Blinding Lights", artistName = "The Weeknd", trackTime = "03:20"),
        Track(id = 2, trackName = "Bad Guy", artistName = "Billie Eilish", trackTime = "03:14"),
        Track(id = 3, trackName = "Believer", artistName = "Imagine Dragons", trackTime = "03:24"),
        Track(id = 4, trackName = "Numb", artistName = "Linkin Park", trackTime = "03:07"),
        Track(id = 5, trackName = "Levitating", artistName = "Dua Lipa", trackTime = "03:23"),
        Track(id = 6, trackName = "Starboy", artistName = "The Weeknd", trackTime = "03:50"),
        Track(id = 7, trackName = "Shape of You", artistName = "Ed Sheeran", trackTime = "03:54"),
        Track(id = 8, trackName = "Lovely", artistName = "Billie Eilish", trackTime = "03:21"),
        Track(id = 9, trackName = "Thunder", artistName = "Imagine Dragons", trackTime = "03:10"),
        Track(id = 10, trackName = "Closer", artistName = "The Chainsmokers", trackTime = "04:05")
    )

    fun searchTracks(query: String): List<Track> {
        if (query.isBlank()) return emptyList()
        val normalized = query.trim().lowercase()
        return listTracks.filter {
            it.trackName.lowercase().contains(normalized) ||
                    it.artistName.lowercase().contains(normalized)
        }
    }
}