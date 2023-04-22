package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("change_keys")
    val change_keys: List<String>,
    @SerializedName("images")
    val imagesConfig: ImagesConfig
)