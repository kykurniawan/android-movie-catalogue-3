package com.rizky.submissionthreemoviecatalogue.ui.favorite.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rizky.submissionthreemoviecatalogue.R
import com.rizky.submissionthreemoviecatalogue.db.MovieFavoriteHelper
import com.rizky.submissionthreemoviecatalogue.helper.MovieMappingHelper
import com.rizky.submissionthreemoviecatalogue.ui.favorite.adapter.MovieFavoriteAdapter
import com.rizky.submissionthreemoviecatalogue.ui.movie.MovieItems
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieFavoriteFragment : Fragment() {

    private lateinit var adapter: MovieFavoriteAdapter
    private lateinit var movieFavoriteHelper: MovieFavoriteHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movie_favorite.layoutManager = LinearLayoutManager(requireContext())
        rv_movie_favorite.setHasFixedSize(true)
        adapter = MovieFavoriteAdapter(requireActivity())
        rv_movie_favorite.adapter = adapter

        movieFavoriteHelper = MovieFavoriteHelper.getInstance(requireContext())
        movieFavoriteHelper.open()

        if (savedInstanceState == null) {
            loadMovieFavoriteAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<MovieItems>(EXTRA_STATE)
            if (list != null) {
                adapter.listMovieFavorite = list
            }
        }

    }

    override fun onResume() {
        super.onResume()
        loadMovieFavoriteAsync()
    }

    private fun loadMovieFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovieFavorite = async(Dispatchers.IO) {
                val cursor = movieFavoriteHelper.queryAll()
                MovieMappingHelper.mapCursorToArrayList(cursor)
            }
            val moviefavorite = deferredMovieFavorite.await()
            if (moviefavorite.size > 0) {
                adapter.listMovieFavorite = moviefavorite
            } else {
                adapter.listMovieFavorite = ArrayList()
                showSnackbarMessage(resources.getString(R.string.no_data_for_now))
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listMovieFavorite)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieFavoriteHelper.close()
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_movie_favorite, message, Snackbar.LENGTH_SHORT).show()
    }


}
