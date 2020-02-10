package com.rizky.submissionthreemoviecatalogue.ui.tvshow

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
import com.rizky.submissionthreemoviecatalogue.db.TvShowFavoriteHelper
import com.rizky.submissionthreemoviecatalogue.helper.TvShowMappingHelper
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.img_backdrop
import kotlinx.android.synthetic.main.activity_movie_detail.img_poster
import kotlinx.android.synthetic.main.activity_movie_detail.progressBar
import kotlinx.android.synthetic.main.activity_movie_detail.tv_overview
import kotlinx.android.synthetic.main.activity_movie_detail.tv_popularity
import kotlinx.android.synthetic.main.activity_movie_detail.tv_rating
import kotlinx.android.synthetic.main.activity_movie_detail.tv_title
import kotlinx.android.synthetic.main.activity_movie_detail.tv_vote
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import kotlinx.android.synthetic.main.activity_tvshow_detail.btn_set_favorite

class TvshowDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)
        val tvShowFavoriteHelper = TvShowFavoriteHelper.getInstance(this)
        tvShowFavoriteHelper.open()

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


        val cursor = tvShowFavoriteHelper.queryById(tvshow.id.toString())
        val tvShowFavorite = TvShowMappingHelper.mapCursorToArrayList(cursor)

        if (intent.getStringExtra("from") == "Favorite") {
            btn_set_favorite.setText(R.string.delete_from_favorite)
            btn_set_favorite.setOnClickListener {
                deleteFromFavorite(tvshow, tvShowFavoriteHelper)
            }
        } else {
            if (!tvShowFavorite.isEmpty()) {
                btn_set_favorite.setText(R.string.delete_from_favorite)
                btn_set_favorite.setOnClickListener {
                    deleteFromFavorite(tvshow, tvShowFavoriteHelper)
                }
            } else {
                btn_set_favorite.setText(R.string.add_to_favorite)
                btn_set_favorite.setOnClickListener {
                    saveToFavorite(tvshow, tvShowFavoriteHelper)
                }
            }
        }

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

    private fun saveToFavorite(tvshow: TvshowItems, tvShowFavoriteHelper: TvShowFavoriteHelper) {
        val values = ContentValues()
        values.put(DatabaseContract.TvShowFavoriteColumns._ID, tvshow.id)
        values.put(DatabaseContract.TvShowFavoriteColumns.NAME, tvshow.name)
        values.put(DatabaseContract.TvShowFavoriteColumns.FIRST_AIR_DATE, tvshow.first_air_date)
        values.put(DatabaseContract.TvShowFavoriteColumns.OVERVIEW, tvshow.overview)
        values.put(DatabaseContract.TvShowFavoriteColumns.POSTER_PATH, tvshow.poster_path)
        values.put(DatabaseContract.TvShowFavoriteColumns.BACKDROP_PATH, tvshow.backdrop_path)
        values.put(DatabaseContract.TvShowFavoriteColumns.VOTE_COUNT, tvshow.vote_count)
        values.put(DatabaseContract.TvShowFavoriteColumns.POPULARITY, tvshow.popularity)
        values.put(DatabaseContract.TvShowFavoriteColumns.VOTE_AVERAGE, tvshow.vote_average)
        tvShowFavoriteHelper.insert(values)

        btn_set_favorite.setText(R.string.delete_from_favorite)
        btn_set_favorite.setOnClickListener {
            deleteFromFavorite(tvshow, tvShowFavoriteHelper)
        }
        Snackbar.make(
            tvshow_detail,
            resources.getString(R.string.added_to_favorite),
            Snackbar.LENGTH_SHORT
        ).show()

    }

    private fun deleteFromFavorite(
        tvshow: TvshowItems,
        tvShowFavoriteHelper: TvShowFavoriteHelper
    ) {
        tvShowFavoriteHelper.deleteById(tvshow.id.toString())
        btn_set_favorite.setText(R.string.add_to_favorite)
        btn_set_favorite.setOnClickListener {
            saveToFavorite(tvshow, tvShowFavoriteHelper)
        }
        Snackbar.make(
            tvshow_detail,
            resources.getString(R.string.deleted_from_favorite),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
