package com.mr_nbody16.moviewviewer.view_models

import androidx.lifecycle.*
import com.mr_nbody16.moviewviewer.domains.movieApis.repositories.MovieDetailsRepo
import com.mr_nbody16.moviewviewer.models.CompanyResponse
import com.mr_nbody16.moviewviewer.models.MovieDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieDetailsRepo: MovieDetailsRepo,
    private val apiKey: String
) : ViewModel() {

    companion object {
        fun build(movieDetailsRepo: MovieDetailsRepo, apiKey: String) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieDetailsViewModel(movieDetailsRepo, apiKey) as T
                }
            }
    }

    private val _movieDetailsLiveData: MutableLiveData<MovieDetailsResponse?> = MutableLiveData()
    private val _companyDetailsLiveData: MutableLiveData<CompanyResponse?> = MutableLiveData()


    fun getMovieDetailsLiveData(): LiveData<MovieDetailsResponse?> = _movieDetailsLiveData
    fun getCompanyDetailsLiveData(): LiveData<CompanyResponse?> = _companyDetailsLiveData


    fun getDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _movieDetailsLiveData.postValue(
                    movieDetailsRepo.getMovieDetails(apiKey, movieId)
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    fun getCompanyDetails(companyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _companyDetailsLiveData.postValue(
                    movieDetailsRepo.getCompanyDetails(
                        apiKey,
                        companyId
                    )
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

}