package com.rizky.submissionthreemoviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rizky.submissionthreemoviecatalogue.R
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : Fragment() {
    private lateinit var adapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        this.adapter = MovieAdapter()
        this.adapter.notifyDataSetChanged()
        this.rv_movie.setHasFixedSize(true)
        this.rv_movie.layoutManager = LinearLayoutManager(this.context)
        this.rv_movie.adapter = this.adapter

        this.movieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieViewModel::class.java)

        this.movieViewModel.setMovie()
        showLoading(true)

        this.movieViewModel.getmovie().observe(this.viewLifecycleOwner, Observer { movieItems ->
            if (movieItems != null) {
                this.adapter.setData(movieItems)
                showLoading(false)
            } else {
                this.adapter.setData(ArrayList())
                val snackbar = Snackbar.make(view, R.string.failed_data, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}