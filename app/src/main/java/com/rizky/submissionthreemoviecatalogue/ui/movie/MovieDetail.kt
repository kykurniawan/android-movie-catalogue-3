package com.rizky.submissionthreemoviecatalogue.ui.movie

import android.content.ContentValues
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.rizky.submissionthreemoviecatalogue.R
import com.rizky.submissionthreemoviecatalogue.db.DatabaseContract
import com.rizky.submissionthreemoviecatalogue.db.MovieFavoriteHelper
import com.rizky.submissionthreemoviecatalogue.helper.MovieMappingHelper
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val movieFavoriteHelper = MovieFavoriteHelper.getInstance(this)
        movieFavoriteHelper.open()

        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieItems

        showLoading(true)

        val backdrop = movie.backdrop_path
        val poster = movie.poster_path
        val backdroplink = "https://image.tmdb.org/t/p/original$backdrop"
        val posterlink = "https://image.tmdb.org/t/p/original$poster"

        Glide
            .with(this)
            .load(backdroplink)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    showLoading(false)
                    Toast.makeText(this@MovieDetail, "Gagal memuat gambar", Toast.LENGTH_SHORT)
                        .show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    showLoading(false)
                    Toast.makeText(this@MovieDetail, "Load Berhasil", Toast.LENGTH_SHORT).show()
                    return false
                }
            })
            .apply(RequestOptions().override(1920, 1080))
            .into(img_backdrop)
        val popular = resources.getString(R.string.popularity)
        val popularity = movie.popularity.toString()
        val vot = resources.getString(R.string.vote)
        val vote = movie.vote_count.toString()
        val rat = resources.getString(R.string.rating)
        val rating = movie.vote_average.toString()
        val rel = resources.getString(R.string.release)
        val release = movie.release_date

        tv_title.text = movie.title
        tv_popularity.text = popular + popularity
        tv_vote.text = vot + vote
        tv_rating.text = rat + rating
        tv_release.text = rel + release
        tv_overview.text = movie.overview

        Glide
            .with(this)
            .load(posterlink)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(this@MovieDetail, "Gagal memuat gambar", Toast.LENGTH_SHORT)
                        .show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(this@MovieDetail, "Load Berhasil", Toast.LENGTH_SHORT).show()
                    return false
                }
            })
            .placeholder(CircularProgressDrawable(this))
            .apply(RequestOptions().override(400, 510))
            .into(img_poster)

        val cursor = movieFavoriteHelper.queryById(movie.id.toString())
        val moviefavorite = MovieMappingHelper.mapCursorToArrayList(cursor)

        if (intent.getStringExtra("from") == "Favorite") {
            btn_set_favorite.setText(R.string.delete_from_favorite)
            btn_set_favorite.setOnClickListener {
                deleteFromFavorite(movie, movieFavoriteHelper)
            }
        } else {
            if (!moviefavorite.isEmpty()) {
                btn_set_favorite.setText(R.string.delete_from_favorite)
                btn_set_favorite.setOnClickListener {
                    deleteFromFavorite(movie, movieFavoriteHelper)
                }
            } else {
                btn_set_favorite.setText(R.string.add_to_favorite)
                btn_set_favorite.setOnClickListener {
                    saveToFavorite(movie, movieFavoriteHelper)
                }
            }
        }

        val actionbar = supportActionBar
        actionbar?.title = movie.title
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayUseLogoEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun saveToFavorite(movie: MovieItems, movieFavoriteHelper: MovieFavoriteHelper) {
        val values = ContentValues()
        values.put(DatabaseContract.MovieFavoriteColumns._ID, movie.id)
        values.put(DatabaseContract.MovieFavoriteColumns.TITLE, movie.title)
        values.put(DatabaseContract.MovieFavoriteColumns.RELEASE_DATE, movie.release_date)
        values.put(DatabaseContract.MovieFavoriteColumns.OVERVIEW, movie.overview)
        values.put(DatabaseContract.MovieFavoriteColumns.POSTER_PATH, movie.poster_path)
        values.put(DatabaseContract.MovieFavoriteColumns.VOTE_COUNT, movie.vote_count)
        values.put(DatabaseContract.MovieFavoriteColumns.POPULARITY, movie.popularity)
        values.put(DatabaseContract.MovieFavoriteColumns.BACKDROP_PATH, movie.backdrop_path)
        values.put(DatabaseContract.MovieFavoriteColumns.VOTE_AVERAGE, movie.vote_average)

        movieFavoriteHelper.insert(values)
        btn_set_favorite.setText(R.string.delete_from_favorite)
        btn_set_favorite.setOnClickListener {
            deleteFromFavorite(movie, movieFavoriteHelper)
        }

        Snackbar.make(movie_detail, R.string.added_to_favorite, Snackbar.LENGTH_SHORT).show()

    }

    private fun deleteFromFavorite(movie: MovieItems, movieFavoriteHelper: MovieFavoriteHelper) {
        movieFavoriteHelper.deleteById(movie.id.toString())
        btn_set_favorite.setText(R.string.add_to_favorite)
        btn_set_favorite.setOnClickListener {
            saveToFavorite(movie, movieFavoriteHelper)
        }

        Snackbar.make(movie_detail, R.string.deleted_from_favorite, Snackbar.LENGTH_SHORT).show()
    }
}
