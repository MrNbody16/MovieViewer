package com.mr_nbody16.moviewviewer

import com.mr_nbody16.moviewviewer.domains.movieApis.retrofit.RetrofitClient
import com.mr_nbody16.moviewviewer.helper.Constants
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import retrofit2.Retrofit
import kotlin.coroutines.coroutineContext




class RetrofitApiTest {


    @Test
    fun testRetrofitInstance() {
        val instance : RetrofitClient? = RetrofitClient.getClient()
        assert(instance != null)
        assert(instance?.apis != null)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    suspend fun testMovieListEndPoint() {
        val retrofitClinet = RetrofitClient.getClient()
        GlobalScope.launch(Dispatchers.IO){
            val movieListResponse = retrofitClinet?.apis?.getMovieList(Constants.API_KEY , 1)
            assert(movieListResponse != null)
            val errorBody = movieListResponse?.errorBody()
            assert(errorBody == null)
            assert(movieListResponse?.code() == 200)
        }


    }

}