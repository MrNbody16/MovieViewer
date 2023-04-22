package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val iso_639_1: String? = null,
    @SerializedName("name")
    val name: String? = null
)