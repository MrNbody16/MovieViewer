package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("logo_path")
    val logo_path: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("origin_country")
    val origin_country: String? = null
)