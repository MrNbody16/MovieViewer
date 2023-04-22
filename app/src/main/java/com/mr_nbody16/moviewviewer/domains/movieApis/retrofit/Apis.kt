package com.mr_nbody16.moviewviewer.domains.movieApis.retrofit

import com.mr_nbody16.moviewviewer.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Apis {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }


    @GET("movie/popular")
    suspend fun  getMovieList(@Query("api_key") api : String , @Query("page") page : Int) : Response<MovieListResponse>?

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path(value = "movie_id")movieId : Int , @Query("api_key")api:String) : Response<MovieDetailsResponse>?

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key")api:String) : Response<GenresResponse>?

    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key")api : String) : Response<ConfigurationResponse?>

    @GET("company/{company_id}")
    suspend fun getCompanyInformation(@Path("company_id")companyId : Int , @Query("api_key")apiKey : String) : Response<CompanyResponse?>

}