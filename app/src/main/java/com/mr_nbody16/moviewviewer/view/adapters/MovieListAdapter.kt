package com.mr_nbody16.moviewviewer.view.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.atiafkar.agentapp.helperClasses.NavOpt
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mr_nbody16.moviewviewer.R
import com.mr_nbody16.moviewviewer.databinding.MovieListItemModelBinding
import com.mr_nbody16.moviewviewer.models.ConfigurationResponse
import com.mr_nbody16.moviewviewer.models.Genre
import com.mr_nbody16.moviewviewer.models.GenresResponse
import com.mr_nbody16.moviewviewer.models.MovieListResponse

class MovieListAdapter(
    private val context: Context,
    private val navController: NavController
) :
    RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    class MyViewHolder(val holderBinding: MovieListItemModelBinding) :
        RecyclerView.ViewHolder(holderBinding.root)


    private var movieList: MovieListResponse? = null
    private var configs: ConfigurationResponse? = null
    private var genreList: HashMap<Int, String>? = null
    private var genresTemp = ""
    private var genresList : GenresResponse? = null
    private var loadingIndicator: CircularProgressDrawable =
        CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }


    fun setMovieList(movieList: MovieListResponse?) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    fun setConfiguration(config: ConfigurationResponse?) {
        this.configs = config
    }

    fun setGenresList(genreList: HashMap<Int, String>?) {
        this.genreList = genreList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieListItemModelBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movieList?.movieResponses?.size ?: 0


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(movieList?.movieResponses?.get(position)) {
            holder.holderBinding.also { binding ->

                binding.moviePosterImg.setOnClickListener {
                    val bundle = Bundle().also {
                        it.putInt("movieId" ,this?.id?:0)
                        val gson = Gson()
                        it.putString("config",gson.toJson(configs?:""))

                    }
                    navController.navigate(R.id.list2Detials  , bundle , NavOpt.build())
                }

                binding.movieTitle.text = this?.title ?: ""
                binding.releaseDate.text = this?.release_date ?: ""
                binding.movieLanguageTxt.text = this?.original_language ?: ""
                if (configs != null) {
                    Glide.with(context)
                        .load(
                            (configs?.imagesConfig?.base_url + configs?.imagesConfig?.backdrop_sizes?.get(
                                1
                            ) + this?.backdrop_path)
                        )
                        .placeholder(loadingIndicator)
                        .error(R.drawable.warning_amber)
                        .into(binding.moviePosterImg)
                } else {
                    binding.moviePosterImg.setImageDrawable(context.getDrawable(R.drawable.warning_amber))
                }
                if (!genreList.isNullOrEmpty() && !this?.genre_ids.isNullOrEmpty()) {
                    genresTemp = ""
                    for (id in this!!.genre_ids!!) {
                        if (genreList!!.containsKey(id)) {
                            if (genresTemp.isEmpty())
                                genresTemp += genreList!![id]
                            else
                                genresTemp += " - " + genreList!![id]
                        }
                    }
                    binding.genreTxt.text = genresTemp
                }
                binding.itemModelParent.setOnClickListener {

                }
            }
        }
    }


}