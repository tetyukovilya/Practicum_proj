package com.tetyukov.practicum_proj.data.network

class RetrofitNetworkClient : NetworkClient {
    override suspend fun doRequest(dto: Any): BaseResponse {
        return BaseResponse(resultCode = 200)
    }
}
