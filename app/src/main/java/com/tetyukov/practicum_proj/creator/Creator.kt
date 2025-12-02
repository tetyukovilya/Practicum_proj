package com.tetyukov.practicum_proj.creator

import android.content.Context
import com.tetyukov.practicum_proj.data.network.RetrofitNetworkClient
import com.tetyukov.practicum_proj.data.repository.TracksRepositoryImpl
import com.tetyukov.practicum_proj.domain.TracksRepository

object Creator {
    // This function will create the repository
    fun getTracksRepository(context: Context): TracksRepository {
        // First, create an instance of your Storage
        val storage = Storage()

        // Now, pass BOTH storage and context to the RetrofitNetworkClient constructor
        val networkClient = RetrofitNetworkClient(storage, context)

        // Finally, create the repository
        return TracksRepositoryImpl(networkClient)
    }
}