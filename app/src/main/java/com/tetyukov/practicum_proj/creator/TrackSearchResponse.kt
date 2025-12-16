package com.tetyukov.practicum_proj.creator

import com.tetyukov.practicum_proj.domain.TrackDto
import com.tetyukov.practicum_proj.domain.BaseResponse

class TrackSearchResponse(
    val results: List<TrackDto>
) : BaseResponse()