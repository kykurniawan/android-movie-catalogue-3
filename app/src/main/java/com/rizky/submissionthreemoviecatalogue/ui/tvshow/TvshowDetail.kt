package com.rizky.submissionthreemoviecatalogue.ui.tvshow

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
import com.rizky.submissionthreemoviecatalogue.R
import kotlinx.android.synthetic.main.activity_movie_detail.img_backdrop
import kotlinx.android.synthetic.main.activity_movie_detail.img_poster
import kotlinx.android.synthetic.main.activity_movie_detail.progressBar
import kotlinx.android.synthetic.main.activity_movie_detail.tv_overview
import kotlinx.android.synthetic.main.activity_movie_detail.tv_popularity
import kotlinx.android.synthetic.main.activity_movie_detail.tv_rating
import kotlinx.android.synthetic.main.activity_movie_detail.tv_title
import kotlinx.android.synthetic.main.activity_movie_detail.tv_vote
import kotlinx.android.synthetic.main.activity_tvshow_detail.*

class TvshowDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        val tvshow = intent.getParcelableExtra(EXTRA_TVSHOW) as TvshowItems

        showLoading(true)

        val backdrop = tvshow.backdrop_path
        val poster = tvshow.poster_path
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
                    Toast.makeText(this@TvshowDetail, "Gagal memuat gambar", Toast.LENGTH_SHORT)
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
                    Toast.makeText(this@TvshowDetail, "Load Berhasil", Toast.LENGTH_SHORT).show()
                    return false
                }
            })
            .apply(RequestOptions().override(1920, 1080))
            .into(img_backdrop)
        val popular = resources.getString(R.string.popularity)
        val popularity = tvshow.popularity.toString()
        val vot = resources.getString(R.string.vote)
        val vote = tvshow.vote_count.toString()
        val rat = resources.getString(R.string.rating)
        val rating = tvshow.vote_average.toString()
        val air_date = resources.getString(R.string.air_date)
        val first_air_date = tvshow.first_air_date

        tv_title.text = tvshow.name
        tv_popularity.text = popular + popularity
        tv_vote.text = vot + vote
        tv_rating.text = rat + rating
        tv_air_date.text = air_date + first_air_date
        tv_overview.text = tvshow.overview

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
                    Toast.makeText(this@TvshowDetail, "Gagal memuat gambar", Toast.LENGTH_SHORT)
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
                    Toast.makeText(this@TvshowDetail, "Load Berhasil", Toast.LENGTH_SHORT).show()
                    return false
                }
            })
            .placeholder(CircularProgressDrawable(this))
            .apply(RequestOptions().override(400, 510))
            .into(img_poster)



        val actionbar = supportActionBar
        actionbar?.title = tvshow.name
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
