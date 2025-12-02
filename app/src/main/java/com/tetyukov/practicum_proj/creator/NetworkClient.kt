package com.tetyukov.practicum_proj.creator

import com.tetyukov.practicum_proj.domain.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}