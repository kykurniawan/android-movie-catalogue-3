package com.rizky.submissionthreemoviecatalogue.ui.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.rizky.submissionthreemoviecatalogue.R
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieItems

        val backdrop = movie.backdrop_path
        val backdroplink = "https://image.tmdb.org/t/p/original$backdrop"

        Glide.with(this)
            .load(backdroplink)
            .placeholder(CircularProgressDrawable(this))
            .into(img_backdrop)

        val actionbar = supportActionBar
        actionbar?.title = movie.title
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
