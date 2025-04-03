package com.example.c7_2

import com.squareup.moshi.Json


data class GitResponse(
    @Json(name = "name") val name: String,
    @Json(name = "html_url") val url: String
)

