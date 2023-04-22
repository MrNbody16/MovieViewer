package com.mr_nbody16.moviewviewer.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mr_nbody16.moviewviewer.databinding.FragmentMovieListBinding
import com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces.ConfigurationDataSource
import com.mr_nbody16.moviewviewer.domains.movieApis.data_soruces.MovieListDataSource
import com.mr_nbody16.moviewviewer.domains.movieApis.repositories.ConfigurationRepo
import com.mr_nbody16.moviewviewer.domains.movieApis.repositories.MovieListRepo
import com.mr_nbody16.moviewviewer.helper.Constants
import com.mr_nbody16.moviewviewer.helper.Dialog
import com.mr_nbody16.moviewviewer.helper.LoadingDialog
import com.mr_nbody16.moviewviewer.models.MovieListResponse
import com.mr_nbody16.moviewviewer.view.adapters.MovieListAdapter
import com.mr_nbody16.moviewviewer.view_models.MovieListViewModel

class MovieListFragment : Fragment() {

    companion object {
        private const val configId = 1
        private const val movieListId = 2
        private const val genresId = 3
    }


    private lateinit var binding : FragmentMovieListBinding
    /*private var loadingDialog: LoadingDialog? = null
    private var newPageLoadingDialog : LoadingDialog? = null*/

    private var loadedItems = mutableMapOf<Int, Boolean>()
    private var errors = mutableMapOf<Int, String>()

    private var pageCounter = 1
    private var totalPages : Int? = null

    private val viewModel: MovieListViewModel by viewModels {
        MovieListViewModel.build(
            ConfigurationRepo(ConfigurationDataSource()),
            MovieListRepo(MovieListDataSource()),
            Constants.API_KEY
        )
    }

    private lateinit var adapter : MovieListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        /*loadingDialog = LoadingDialog.build(requireContext(), Constants.FETCHING_CONFIGS)
        newPageLoadingDialog = LoadingDialog.build(requireContext() , "Loading Page Info")*/
        binding.loadingIndicator.visibility = View.VISIBLE
        resetLoadedList()
        adapter = MovieListAdapter(requireContext() , findNavController())
        binding.movieListRecycler.apply {
            adapter = this@MovieListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL,false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.configurationLiveData().observe(viewLifecycleOwner) { config ->
            adapter.setConfiguration(config)
            if (config != null) {
                loadedItems[configId] = true
                adapter.notifyDataSetChanged()
            }
            else {
                requireActivity().runOnUiThread {
                    loadedItems[configId] = false
                    binding.warningIcon.visibility = View.VISIBLE
                    errors[configId] = "Could not load Configurations from api please check logs"
                }
            }

        }


        viewModel.movieListLiveData().observe(viewLifecycleOwner) { movies ->
            adapter.setMovieList(movies)
            totalPages = movies?.total_pages
            if (movies != null) {
                loadedItems[movieListId] = true
                requireActivity().runOnUiThread {
                    binding.loadingIndicator.visibility = View.GONE
                    setPageCounter()
                }
            } else {
                requireActivity().runOnUiThread {
                    binding.loadingIndicator.visibility = View.GONE
                    loadedItems[movieListId] = false
                    binding.warningIcon.visibility = View.VISIBLE
                    errors[movieListId] = "Could not load movie list please try again"
                }
            }

        }

        viewModel.genresLiveData().observe(viewLifecycleOwner) { genres ->
            adapter.setGenresList(genres)
            if (genres != null) {
                loadedItems[genresId] = true
                adapter.notifyDataSetChanged()
                // let genres load to list items
            } else {
                requireActivity().runOnUiThread {
                    loadedItems[genresId] = false
                    binding.warningIcon.visibility = View.VISIBLE
                    errors[genresId] = "Could not load full list of movie genres"
                }
            }
        }

        binding.warningIcon.setOnClickListener {
            showWarningError()
        }

        viewModel.getAll(pageCounter)


        binding.nextPage.setOnClickListener {
            if(totalPages!=null) {
                if (pageCounter < totalPages!!) {
                    adapter.setMovieList(null)
                    binding.loadingIndicator.visibility = View.VISIBLE
                    pageCounter++
                    setPageCounter()
                    viewModel.getMovieList(pageCounter)
                } else if (pageCounter == totalPages) {
                    Toast.makeText(requireContext() , "This is the last page!" , Toast.LENGTH_LONG).show()
                }
            }
        }


        binding.previousPage.setOnClickListener {
            if (totalPages != null) {
                if(pageCounter > 1) {
                    adapter.setMovieList(null)
                    binding.loadingIndicator.visibility = View.VISIBLE
                    pageCounter--
                    setPageCounter()
                    viewModel.getMovieList(pageCounter)
                } else if (pageCounter == 1 ) {
                    Toast.makeText(requireContext() , "This is the first page!" , Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun setPageCounter() {
        binding.pageCounter.text = "$pageCounter / ${totalPages?:"null"}"
    }


    private fun showWarningError() {
        var finalError = "Found These errors please confirm and let application try again"
        errors.forEach {
            finalError += "\n\t$it"
        }
        var mDialog: Dialog? = null
        mDialog = Dialog.build(
            requireContext(),
            finalError,
            Dialog.BtnRequirements.get("Lets fetch again") {
                mDialog?.dismiss()
                /*when {
                    errors.containsValue()
                    errors.containsKey(configId) && errors.containsKey(movieListId) -> {
                        loadedItems[movieListId] = false
                        loadedItems[configId] = false
                        viewModel.getAll()
                    }
                    errors.containsKey(configId) -> {
                        isConfigLoaded = false
                        viewModel.getConfig()
                    }

                    errors.containsKey(movieListId) -> {
                        isMovieListLoaded = false
                        viewModel.getMovieList(pageCounter)
                    }
                }*/
                requireActivity().runOnUiThread {
                    binding.loadingIndicator.visibility = View.VISIBLE
                }
                errors.clear()
                resetLoadedList()
                viewModel.getAll(pageCounter)
                requireActivity().runOnUiThread {
                    binding.warningIcon.visibility = View.GONE
                }
            })
        mDialog.setCancelable(true)
        mDialog.show()
    }

    private fun resetLoadedList() {
        loadedItems = mutableMapOf(
            Pair(configId, false), Pair(movieListId, false), Pair(
                genresId, false
            )
        )
    }

}