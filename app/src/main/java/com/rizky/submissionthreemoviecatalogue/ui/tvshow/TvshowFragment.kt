package com.rizky.submissionthreemoviecatalogue.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizky.submissionthreemoviecatalogue.R
import kotlinx.android.synthetic.main.fragment_movie.progressBar
import kotlinx.android.synthetic.main.fragment_tvshow.*

class TvshowFragment : Fragment() {

    private lateinit var adapter: TvshowAdapter
    private lateinit var tvshowViewModel: TvshowViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        this.adapter = TvshowAdapter()
        this.adapter.notifyDataSetChanged()

        this.rv_tvshow.layoutManager = LinearLayoutManager(this.context)
        this.rv_tvshow.adapter = this.adapter

        this.tvshowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvshowViewModel::class.java)

        this.tvshowViewModel.setTvshow()
        showLoading(true)

        this.tvshowViewModel.gettvshow().observe(this.viewLifecycleOwner, Observer { tvshowItems ->
            if (tvshowItems != null) {
                this.adapter.setData(tvshowItems)
                showLoading(false)
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
