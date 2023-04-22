package com.mr_nbody16.moviewviewer.domains.movieApis.repositories

import com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces.ConfigurationDataSource

class ConfigurationRepo(private val configurationDs : ConfigurationDataSource) {

    suspend fun getConfig(apiKey: String) = configurationDs.getConfiguration(apiKey)

    suspend fun getGenres(apiKey: String) = configurationDs.getGenres(apiKey)
}