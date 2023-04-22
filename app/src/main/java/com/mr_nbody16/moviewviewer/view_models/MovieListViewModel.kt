package com.mr_nbody16.moviewviewer.view_models

import androidx.lifecycle.*
import com.mr_nbody16.moviewviewer.domains.movieApis.repositories.ConfigurationRepo
import com.mr_nbody16.moviewviewer.domains.movieApis.repositories.MovieListRepo
import com.mr_nbody16.moviewviewer.models.ConfigurationResponse
import com.mr_nbody16.moviewviewer.models.GenresResponse
import com.mr_nbody16.moviewviewer.models.MovieListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val configurationRepo : ConfigurationRepo ,
    private val movieListRepo: MovieListRepo,
    private val apiKey: String
) :
    ViewModel() {

    companion object {
        fun build(configurationRepo: ConfigurationRepo , movieListRepo: MovieListRepo, apiKey: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieListViewModel(configurationRepo , movieListRepo, apiKey) as T
                }
            }
    }

    private val _movieListLiveData: MutableLiveData<MovieListResponse?> = MutableLiveData()
    private val _configLiveData : MutableLiveData<ConfigurationResponse?> = MutableLiveData()
    private val _genresLiveData : MutableLiveData<HashMap<Int,String>?> = MutableLiveData()

    fun movieListLiveData(): LiveData<MovieListResponse?> = _movieListLiveData
    fun configurationLiveData() : LiveData<ConfigurationResponse?> = _configLiveData
    fun genresLiveData() : LiveData<HashMap<Int,String>?> = _genresLiveData

    fun getMovieList(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _movieListLiveData.postValue(movieListRepo.getMovieList(apiKey, page))
            }catch (e :java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _configLiveData.postValue(configurationRepo.getConfig(apiKey))
            }catch (e : java.lang.Exception){
                e.printStackTrace()
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
            _genresLiveData.postValue(configurationRepo.getGenres(apiKey))
            } catch (e :Exception){
                e.printStackTrace()
            }
        }
    }


    fun getAll(page : Int) {
        getConfig()
        getGenres()
        getMovieList(page)
    }


}