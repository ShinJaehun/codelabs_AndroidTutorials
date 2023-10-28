package com.example.android.marsphotos.network

import com.squareup.moshi.Json

data class MarsPhoto (
    val id: String,
//    val img_src: String
    @Json(name="img_src")
    val imgSrcUrl: String
)