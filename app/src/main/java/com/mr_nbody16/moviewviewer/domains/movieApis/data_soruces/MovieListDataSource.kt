package com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces

import android.util.Log
import com.mr_nbody16.moviewviewer.domains.movieApis.retrofit.RetrofitClient
import com.mr_nbody16.moviewviewer.helper.Constants
import com.mr_nbody16.moviewviewer.models.MovieListResponse
import retrofit2.Call
import retrofit2.Response

class MovieListDataSource {
    suspend fun getMovieList(api: String, page: Int): MovieListResponse? {
        var result: MovieListResponse? = null
        /*RetrofitClient.getClient()?.apis?.getMovieList(api, page)?.enqueue(object : retrofit2.Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                response?.let {
                    if(it.isSuccessful)
                        result = it.body()
                    else {
                        Log.e(Constants.TAG ,"failed to fetch movieList : ${it.errorBody()?:"no Error or null"}")
                        result = null
                    }
                }
            }
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e(Constants.TAG ,"failed to fetch movieList : ${t.message?:"no Error or null"}")
                result = null
            }

        })*/
        var response : Response<MovieListResponse>? = null
        try {
            response = RetrofitClient.getClient()?.apis?.getMovieList(api, page)
        }catch (e :java.lang.Exception) {
            e.printStackTrace()
        }


        response?.let {
            if (it.isSuccessful)
                result = it.body()
            else {
                Log.e(
                    Constants.TAG,
                    "failed to fetch movieList : ${it.errorBody() ?: "no Error or null"}"
                )
                result = null
            }
        }

        return result
    }
}