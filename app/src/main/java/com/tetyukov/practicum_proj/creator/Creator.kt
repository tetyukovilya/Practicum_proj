package com.tetyukov.practicum_proj.creator

import android.content.Context
import com.tetyukov.practicum_proj.data.network.RetrofitNetworkClient
import com.tetyukov.practicum_proj.data.repository.TracksRepositoryImpl
import com.tetyukov.practicum_proj.domain.TracksRepository

object Creator {
    fun getTracksRepository(context: Context): TracksRepository {
        val appContext = context.applicationContext
        val storage = Storage(appContext)
        val networkClient = RetrofitNetworkClient(storage, context)
        return TracksRepositoryImpl(networkClient)
    }
}

