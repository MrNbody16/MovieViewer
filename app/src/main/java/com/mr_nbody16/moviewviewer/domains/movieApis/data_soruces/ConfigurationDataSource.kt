package com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces

import android.provider.MediaStore.Audio.Genres
import android.util.Log
import com.mr_nbody16.moviewviewer.domains.movieApis.retrofit.RetrofitClient
import com.mr_nbody16.moviewviewer.helper.Constants
import com.mr_nbody16.moviewviewer.models.ConfigurationResponse
import com.mr_nbody16.moviewviewer.models.GenresResponse
import retrofit2.Call
import retrofit2.Response

class ConfigurationDataSource {
    suspend fun getConfiguration(api: String): ConfigurationResponse? {
        var result: ConfigurationResponse? = null

        /*RetrofitClient.getClient()?.apis?.getConfiguration(api)?.enqueue(object : retrofit2.Callback<ConfigurationResponse?> {
            override fun onResponse(
                call: Call<ConfigurationResponse?>,
                response: Response<ConfigurationResponse?>
            ) {
                response?.let {
                    if(it.isSuccessful)
                        result = it.body()
                    else {
                        result = null
                        Log.e(Constants.TAG ,"failed to fetch configurations : ${it.errorBody()?:"no Error or null"}")
                    }
                }
            }

            override fun onFailure(call: Call<ConfigurationResponse?>, t: Throwable) {
                result = null
                Log.e(Constants.TAG , "failed to fetch configurations : ${t.message?:"no Error or null"}")

            }
        })
        return result
*/
        var response: Response<ConfigurationResponse?>? = null

        try {
            response = RetrofitClient.getClient()?.apis?.getConfiguration(api)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        response?.let {
            if (it.isSuccessful)
                result = it.body()
            else {
                result = null
                Log.e(
                    Constants.TAG,
                    "failed to fetch configurations : ${it.errorBody() ?: "no Error or null"}"
                )
            }
        }
        return result


    }


    suspend fun getGenres(apiKey: String): HashMap<Int, String>? {
        var result: HashMap<Int, String>? = null
        /*RetrofitClient.getClient()?.apis?.getGenres(apiKey)?.enqueue(object : retrofit2.Callback<GenresResponse?> {
            override fun onResponse(
                call: Call<GenresResponse?>,
                response: Response<GenresResponse?>
            ) {

            }

            override fun onFailure(call: Call<GenresResponse?>, t: Throwable) {
                result = null
                Log.e(Constants.TAG , "failed to fetch genres : ${t.message?:"no Error or null"}")
            }

        })*/
        var response: Response<GenresResponse>? = null
        try {
            response = RetrofitClient.getClient()?.apis?.getGenres(apiKey)
        }catch (e : Exception){
            e.printStackTrace()
        }

        response?.let { response ->
            if (response.isSuccessful) {
                response.body()?.genres?.let { mGenres ->
                    result = hashMapOf()
                    for (genre in mGenres) {
                        if (genre.id != null && genre.name != null)
                            result!!.put(genre.id, genre.name)
                    }
                }
            } else {
                result = null
                Log.e(
                    Constants.TAG,
                    "failed to fetch genres : ${response.errorBody() ?: "no Error or null"}"
                )
            }
        }
        return result
    }

}