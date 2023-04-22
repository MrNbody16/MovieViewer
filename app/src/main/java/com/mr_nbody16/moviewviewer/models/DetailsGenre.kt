package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class DetailsGenre(
    @SerializedName("id")
    val id: Int ? = null,
    @SerializedName("name")
    val name: String ? = null
)