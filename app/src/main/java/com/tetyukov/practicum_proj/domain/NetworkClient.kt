package com.tetyukov.practicum_proj.domain

open class BaseResponse { var resultCode: Int = 0 }

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}