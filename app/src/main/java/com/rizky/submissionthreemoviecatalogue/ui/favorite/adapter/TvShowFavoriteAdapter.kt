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
import com.rizky.submissionthreemoviecatalogue.db.TvShowFavoriteHelper
import com.rizky.submissionthreemoviecatalogue.ui.favorite.CustomOnItemClickListener
import com.rizky.submissionthreemoviecatalogue.ui.tvshow.TvshowDetail
import com.rizky.submissionthreemoviecatalogue.ui.tvshow.TvshowItems
import kotlinx.android.synthetic.main.item_row_tvshow.view.img_tvshow_poster
import kotlinx.android.synthetic.main.item_row_tvshow.view.tv_tvshow_name
import kotlinx.android.synthetic.main.item_row_tvshow.view.tv_tvshow_overview
import kotlinx.android.synthetic.main.item_row_tvshow_favorite.view.*

class TvShowFavoriteAdapter(private val activity: Activity) :
    RecyclerView.Adapter<TvShowFavoriteAdapter.TvShowFavoriteViewHolder>() {
    var listTvShowFavorite = ArrayList<TvshowItems>()
        set(listTvShowFavorite) {
            this.listTvShowFavorite.clear()
            this.listTvShowFavorite.addAll(listTvShowFavorite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_tvshow_favorite, parent, false)
        return TvShowFavoriteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TvShowFavoriteAdapter.TvShowFavoriteViewHolder,
        position: Int
    ) {
        holder.bind(listTvShowFavorite[position])
    }

    override fun getItemCount(): Int = this.listTvShowFavorite.size

    inner class TvShowFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowFavorite: TvshowItems) {
            with(itemView) {
                tv_tvshow_name.text = tvShowFavorite.name
                tv_tvshow_overview.text = tvShowFavorite.overview
                val poster = tvShowFavorite.poster_path
                val posterLink = "https://image.tmdb.org/t/p/original$poster"
                Glide.with(itemView.context)
                    .load(posterLink)
                    .placeholder(CircularProgressDrawable(this.context))
                    .apply(RequestOptions().override(300, 500))
                    .into(img_tvshow_poster)
                card_view_tvshow_favorite.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, TvshowDetail::class.java)
                                intent.putExtra(TvshowDetail.EXTRA_TVSHOW, tvShowFavorite)
                                intent.putExtra("from", "Favorite")
                                activity.startActivity(intent)
                            }
                        })
                )
                delete_btn.setOnClickListener {
                    val tvShowFavoriteHelper = TvShowFavoriteHelper.getInstance(activity)
                    tvShowFavoriteHelper.open()
                    tvShowFavoriteHelper.deleteById(tvShowFavorite.id.toString())
                    listTvShowFavorite.removeAt(layoutPosition)
                    notifyDataSetChanged()

                    Snackbar.make(
                        card_view_tvshow_favorite,
                        tvShowFavorite.name + " " + resources.getString(R.string.deleted_from_favorite),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}