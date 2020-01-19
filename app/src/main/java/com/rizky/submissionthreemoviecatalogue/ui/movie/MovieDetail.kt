package com.rizky.submissionthreemoviecatalogue.ui.movie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.rizky.submissionthreemoviecatalogue.R
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.progressBar
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

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
}
