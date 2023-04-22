package com.mr_nbody16.moviewviewer.domains.movieApis.repositories

import com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces.MovieListDataSource
import com.mr_nbody16.moviewviewer.models.MovieListResponse

class MovieListRepo(val movieListDs : MovieListDataSource) {

    suspend fun getMovieList(apiKey : String , page : Int) = movieListDs.getMovieList(apiKey , page)


}