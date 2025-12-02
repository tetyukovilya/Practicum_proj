package com.tetyukov.practicum_proj.data.repository

import com.tetyukov.practicum_proj.creator.TrackSearchRequest
import com.tetyukov.practicum_proj.domain.TracksRepository
import com.tetyukov.practicum_proj.domain.Track
import com.tetyukov.practicum_proj.data.network.RetrofitNetworkClient
import kotlinx.coroutines.delay

class TracksRepositoryImpl(private val networkClient: RetrofitNetworkClient): TracksRepository {
    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            response.results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(minutes, seconds % 60)
                Track(it.trackName, it.artistName, trackTime)
            }
        } else emptyList()
    }
}