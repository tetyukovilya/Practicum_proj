package com.tetyukov.practicum_proj.data.network

import android.content.Context
import com.tetyukov.practicum_proj.creator.Storage
import com.tetyukov.practicum_proj.creator.TrackSearchRequest
import com.tetyukov.practicum_proj.creator.TrackSearchResponse
import com.tetyukov.practicum_proj.domain.NetworkClient

class RetrofitNetworkClient(private val storage: Storage, context: Context) : NetworkClient {
    override fun doRequest(dto: Any): TrackSearchResponse {
        val searchList = storage.search((dto as TrackSearchRequest).expression)
        return TrackSearchResponse(searchList).apply { resultCode = 200 }
    }
}