package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class MovieListResponse(

    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val movieResponses: List<MovieResponse>? = null,
    @SerializedName("total_pages")
    val total_pages: Int? = null,
    @SerializedName("total_results")
    val total_results: Int? = null

)