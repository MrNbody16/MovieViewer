package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<Genre> ? = null
)