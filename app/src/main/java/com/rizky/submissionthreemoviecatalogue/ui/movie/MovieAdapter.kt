package com.rizky.submissionthreemoviecatalogue.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizky.submissionthreemoviecatalogue.R
import kotlinx.android.synthetic.main.item_row_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val mData = ArrayList<MovieItems>()
    fun setData(items: ArrayList<MovieItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MovieViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_movie, viewGroup, false)
        return MovieViewHolder(mView)
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        movieViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieItems: MovieItems) {
            with(itemView) {
                val poster = movieItems.poster_path
                val posterLink = "https://image.tmdb.org/t/p/original$poster"
                Glide.with(itemView.context)
                    .load(posterLink)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_movies_poster)
                tv_movies_title.text = movieItems.title
                tv_movies_overview.text = movieItems.overview

                btn_detail.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Detail For ${movieItems.title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}