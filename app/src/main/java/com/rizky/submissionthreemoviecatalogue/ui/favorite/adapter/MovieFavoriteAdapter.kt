package com.rizky.submissionthreemoviecatalogue.ui.favorite.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.rizky.submissionthreemoviecatalogue.R
import com.rizky.submissionthreemoviecatalogue.db.MovieFavoriteHelper
import com.rizky.submissionthreemoviecatalogue.ui.favorite.CustomOnItemClickListener
import com.rizky.submissionthreemoviecatalogue.ui.movie.MovieDetail
import com.rizky.submissionthreemoviecatalogue.ui.movie.MovieItems
import kotlinx.android.synthetic.main.item_row_movie_favorite.view.*


class MovieFavoriteAdapter(private val activity: Activity) :
    RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder>() {
    var listMovieFavorite = ArrayList<MovieItems>()
        set(listMovieFavorite) {
            this.listMovieFavorite.clear()
            this.listMovieFavorite.addAll(listMovieFavorite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_movie_favorite, parent, false)
        return MovieFavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        holder.bind(listMovieFavorite[position])
    }

    override fun getItemCount(): Int = this.listMovieFavorite.size


    inner class MovieFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(moviefavorite: MovieItems) {
            with(itemView) {
                tv_movies_title.text = moviefavorite.title
                tv_movies_overview.text = moviefavorite.overview
                val poster = moviefavorite.poster_path
                val posterLink = "https://image.tmdb.org/t/p/original$poster"
                Glide.with(itemView.context)
                    .load(posterLink)
                    .placeholder(CircularProgressDrawable(this.context))
                    .apply(RequestOptions().override(300, 500))
                    .into(img_movies_poster)
                card_view.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback {

                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, MovieDetail::class.java)
                                intent.putExtra(MovieDetail.EXTRA_MOVIE, moviefavorite)
                                intent.putExtra("from", "Favorite")
                                activity.startActivity(intent)
                            }
                        })
                )
                delete_btn.setOnClickListener {
                    val movieFavoriteHelper = MovieFavoriteHelper.getInstance(activity)
                    movieFavoriteHelper.open()
                    movieFavoriteHelper.deleteById(moviefavorite.id.toString())

                    listMovieFavorite.removeAt(layoutPosition)
                    notifyDataSetChanged()

                    Snackbar.make(
                        card_view,
                        moviefavorite.title + " " + resources.getString(R.string.delete_from_favorite),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
}