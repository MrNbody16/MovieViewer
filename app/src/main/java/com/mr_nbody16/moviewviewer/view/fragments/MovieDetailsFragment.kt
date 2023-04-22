package com.mr_nbody16.moviewviewer.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.mr_nbody16.moviewviewer.R
import com.mr_nbody16.moviewviewer.databinding.FragmentMovieDetailsBinding
import com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces.MovieDetailsDataSource
import com.mr_nbody16.moviewviewer.domains.movieApis.repositories.MovieDetailsRepo
import com.mr_nbody16.moviewviewer.helper.Constants
import com.mr_nbody16.moviewviewer.helper.Dialog
import com.mr_nbody16.moviewviewer.models.ConfigurationResponse
import com.mr_nbody16.moviewviewer.models.GenresResponse
import com.mr_nbody16.moviewviewer.view.adapters.CompaniesAdapter
import com.mr_nbody16.moviewviewer.view_models.MovieDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment() {


    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var companiesAdapter: CompaniesAdapter
    private var movieId: Int = -1
    private val NA = "N/A"
    private var configuration: ConfigurationResponse? = null


    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModel.build(
            MovieDetailsRepo(MovieDetailsDataSource()), Constants.API_KEY
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        movieId = arguments?.getInt("movieId") ?: -1
        if (movieId == -1) {
            lifecycleScope.launch(Dispatchers.IO) {
                delay(1000)
                lifecycleScope.launch(Dispatchers.Main) {
                    Snackbar.make(binding.root, "Invalid Arguments try again", Snackbar.LENGTH_LONG)
                        .show()
                    findNavController().popBackStack()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companiesAdapter = CompaniesAdapter(requireContext())
        val config: String? = arguments?.getString("config") ?: ""
        val gson = Gson()
        if (!config.isNullOrEmpty()) {
            try {
                configuration = gson.fromJson(config, ConfigurationResponse::class.java)
            }catch (e : Exception){
                e.printStackTrace()
                configuration = null
            }
            companiesAdapter.setConfig(configuration)
        }

        viewModel.getMovieDetailsLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                requireActivity().runOnUiThread {
                    with(binding) {
                        titleTextView.text = it.title ?: NA
                        popularityValueTextView.text = it.popularity.toString() ?: NA
                        releaseDateTextView.text = it.release_date ?: NA
                        revenueTextView.text = "${it.revenue.toString() ?: NA} $"
                        runningTimeTextView.text = it.runtime.toString() ?: NA
                        statusTextView.text = it.status ?: NA
                        var genresTemp = ""
                        for (id in it?.genres ?: mutableListOf()) {

                            if (genresTemp.isEmpty())
                                genresTemp += id.name
                            else
                                genresTemp += " - " + id.name
                        }
                        if (genresTemp.isNullOrEmpty())
                            genresTemp = NA
                        binding.genresTextView.text = genresTemp
                        var spokenTemp = ""
                        for (lan in it.spoken_languages ?: mutableListOf()) {
                            if (spokenTemp.isEmpty())
                                spokenTemp += lan.name
                            else
                                spokenTemp += " - ${lan.name}"
                        }
                        binding.spokenLanguagesTextView.text = spokenTemp
                        binding.taglineTextView.text = it.tagline ?: NA
                        binding.companiesRecycler.apply {
                            adapter = companiesAdapter
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        }
                        companiesAdapter.setDetails(it.production_companies)
                        if (configuration != null) {
                            Glide.with(requireContext())
                                .load(
                                    (configuration?.imagesConfig?.base_url + configuration?.imagesConfig?.backdrop_sizes?.get(
                                        1
                                    ) + it.backdrop_path)
                                ).error(R.drawable.warning_amber)
                                .placeholder(CircularProgressDrawable(requireContext()).apply {
                                    strokeWidth = 5f
                                    centerRadius = 30f
                                    start()
                                })
                                .into(binding.posterImageView)
                        } else
                            binding.posterImageView.setImageDrawable(requireContext().getDrawable(R.drawable.warning_amber))
                        binding.descriptionTextView.text = it.overview ?: NA
                        var productionCountries = ""
                        for (country in it?.production_countries ?: mutableListOf()) {
                            if (productionCountries.isEmpty())
                                productionCountries += country.name
                            else
                                productionCountries += " - ${country.name}"
                        }
                        binding.productionCountries.text = productionCountries
                    }
                    binding.loadingIndicator.visibility = View.GONE
                    binding.mainContainer.visibility = View.VISIBLE
                }
            } else {
                var mDialog: Dialog? = null
                mDialog = Dialog.Companion.build(requireContext(),
                    "Could not load data please check network connection",
                    Dialog.BtnRequirements.get("Ok") {
                        requireActivity().runOnUiThread {
                            mDialog?.dismiss()
                            findNavController().popBackStack()
                        }
                    }
                )
                mDialog.show()
            }
        }


        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        if (movieId != -1)
            viewModel.getDetails(movieId)

    }


}