package com.tetyukov.practicum_proj.creator

import android.content.Context
import com.tetyukov.practicum_proj.data.network.RetrofitNetworkClient
import com.tetyukov.practicum_proj.data.repository.TracksRepositoryImpl
import com.tetyukov.practicum_proj.domain.TracksRepository

object Creator {


    fun getTracksRepository(context: Context): TracksRepository {
        // Если Storage требует контекст — передаём его
        val storage = Storage(context)

        // Инициализируем сетевой клиент с storage и контекстом
        val networkClient = RetrofitNetworkClient(storage, context)

        // Возвращаем репозиторий
        return TracksRepositoryImpl(networkClient)
    }
}