package com.rizky.submissionthreemoviecatalogue.ui.favorite.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rizky.submissionthreemoviecatalogue.R
import com.rizky.submissionthreemoviecatalogue.db.TvShowFavoriteHelper
import com.rizky.submissionthreemoviecatalogue.helper.TvShowMappingHelper
import com.rizky.submissionthreemoviecatalogue.ui.favorite.adapter.TvShowFavoriteAdapter
import com.rizky.submissionthreemoviecatalogue.ui.tvshow.TvshowItems
import kotlinx.android.synthetic.main.fragment_tvshow_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TvshowFavoriteFragment : Fragment() {
    private lateinit var adapter: TvShowFavoriteAdapter
    private lateinit var tvShowFavoriteHelper: TvShowFavoriteHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_tvshow_favorite.layoutManager = LinearLayoutManager(requireContext())
        rv_tvshow_favorite.setHasFixedSize(true)
        adapter = TvShowFavoriteAdapter(requireActivity())
        rv_tvshow_favorite.adapter = adapter

        tvShowFavoriteHelper = TvShowFavoriteHelper(requireContext())
        tvShowFavoriteHelper.open()

        if (savedInstanceState == null) {
            loadTvShowFavoriteAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<TvshowItems>(EXTRA_STATE)
            if (list != null) {
                adapter.listTvShowFavorite = list
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadTvShowFavoriteAsync()
    }

    private fun loadTvShowFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredTvShowFavorite = async(Dispatchers.IO) {
                val cursor = tvShowFavoriteHelper.queryAll()
                TvShowMappingHelper.mapCursorToArrayList(cursor)
            }
            val tvShowFavorite = deferredTvShowFavorite.await()
            if (tvShowFavorite.size > 0) {
                adapter.listTvShowFavorite = tvShowFavorite
            } else {
                adapter.listTvShowFavorite = ArrayList()
                showSnackbarMessage(resources.getString(R.string.no_data_for_now))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listTvShowFavorite)
    }

    override fun onDestroy() {
        super.onDestroy()
        tvShowFavoriteHelper.close()
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_tvshow_favorite, message, Snackbar.LENGTH_SHORT).show()
    }


}
