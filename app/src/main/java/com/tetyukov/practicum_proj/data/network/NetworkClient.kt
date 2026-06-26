package com.tetyukov.practicum_proj.data.network

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}