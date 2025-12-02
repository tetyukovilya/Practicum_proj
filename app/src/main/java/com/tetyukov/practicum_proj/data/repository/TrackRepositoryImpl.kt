package com.tetyukov.practicum_proj.data.repository

import com.tetyukov.practicum_proj.creator.TrackSearchRequest
import com.tetyukov.practicum_proj.domain.TracksRepository
import com.tetyukov.practicum_proj.data.network.RetrofitNetworkClient
import kotlinx.coroutines.delay
import com.tetyukov.practicum_proj.data.dto.TrackDto

class TracksRepositoryImpl(private val networkClient: RetrofitNetworkClient): TracksRepository {
    override suspend fun searchTracks(expression: String): List<TrackDto> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            response.results.map {
                val trackTimeMillisLong = it.trackTimeMillis.toLongOrNull() ?: 0L
                val seconds = trackTimeMillisLong / 1000

                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(minutes, seconds % 60)
                TrackDto(it.trackName, it.artistName, it.trackTimeMillis)
            }
        } else emptyList()
    }
}