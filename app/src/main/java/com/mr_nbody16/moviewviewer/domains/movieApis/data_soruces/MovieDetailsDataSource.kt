package com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces

import android.util.Log
import com.mr_nbody16.moviewviewer.domains.movieApis.retrofit.RetrofitClient
import com.mr_nbody16.moviewviewer.helper.Constants
import com.mr_nbody16.moviewviewer.models.CompanyResponse
import com.mr_nbody16.moviewviewer.models.MovieDetailsResponse
import retrofit2.Response

class MovieDetailsDataSource {

    suspend fun getDetails4Movie(apiKey: String, movieId: Int): MovieDetailsResponse? {
        var result: MovieDetailsResponse? = null
        /*RetrofitClient.getClient()?.apis?.getMovieDetails(movieId , apiKey)?.enqueue(object : retrofit2.Callback<MovieDetailsResponse> {
            override fun onResponse(
                call: Call<MovieDetailsResponse>,
                response: Response<MovieDetailsResponse>
            ) {

            }
            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                Log.e(Constants.TAG ,"failed to fetch movieDetails for $movieId : ${t.message?:"no Error or null"}")
                result = null
            }

        })*/

        var response : Response<MovieDetailsResponse>? = null
        try {
            response = RetrofitClient.getClient()?.apis?.getMovieDetails(movieId, apiKey)
        }catch (e :java.lang.Exception) {
            e.printStackTrace()
        }
        response?.let {
            if (it.isSuccessful)
                result = it.body()
            else {
                Log.e(
                    Constants.TAG,
                    "failed to fetch movieDetails for $movieId : ${it.errorBody() ?: "no Error or null"}"
                )
                result = null
            }
        }

        return result
    }

    suspend fun getCompanyDetails(apiKey:String , companyId : Int) : CompanyResponse? {
        var result : CompanyResponse? = null
        var response : Response<CompanyResponse?>? = null
        try {
            response =
                RetrofitClient.getClient()?.apis?.getCompanyInformation(companyId, apiKey)
        }catch (e :java.lang.Exception){
            e.printStackTrace()
        }
        response?.let {
            if(it.isSuccessful)
                result = response.body()
            else {
                Log.e(
                    Constants.TAG,
                    "failed to fetch CompanyDetails for $companyId : ${it.errorBody() ?: "no Error or null"}"
                )
                result = null
            }
        }
        return result
    }

}