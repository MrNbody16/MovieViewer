package com.mr_nbody16.moviewviewer.domains.movieApis.repositories

import com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces.MovieDetailsDataSource

class MovieDetailsRepo(private val movieDetailsDs : MovieDetailsDataSource) {

    suspend fun getMovieDetails(apiKey : String , movieID : Int) = movieDetailsDs.getDetails4Movie(apiKey , movieID)

    suspend fun getCompanyDetails(apiKey: String , companyId : Int) = movieDetailsDs.getCompanyDetails(apiKey , companyId)

}