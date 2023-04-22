package com.mr_nbody16.moviewviewer.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("adult")
    val adult: Boolean ? = null ,
    @SerializedName("backdrop_path")
    val backdrop_path: String? = null,
    @SerializedName("belongs_to_collection")
    val belongs_to_collection: Any? = null,
    @SerializedName("budget")
    val budget: Int? = null,
    @SerializedName("genres")
    val genres: List<DetailsGenre>? = null,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imdb_id")
    val imdb_id: String? = null,
    @SerializedName("original_language")
    val original_language: String? = null,
    @SerializedName("original_title")
    val original_title: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val poster_path: Any? = null,
    @SerializedName("production_companies")
    val production_companies: List<ProductionCompany>? = null,
    @SerializedName("production_countries")
    val production_countries: List<ProductionCountry>? = null,
    @SerializedName("release_date")
    val release_date: String? = null,
    @SerializedName("revenue")
    val revenue: String? = null,
    @SerializedName("runtime")
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spoken_languages: List<SpokenLanguage>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("tagline")
    val tagline: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val vote_average: Double? = null,
    @SerializedName("vote_count")
    val vote_count: Int? = null
)