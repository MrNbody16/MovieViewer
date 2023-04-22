package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso_3166_1: String? = null,
    @SerializedName("name")
    val name: String? = null
)