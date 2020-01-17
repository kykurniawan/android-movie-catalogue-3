package com.rizky.submissionthreemoviecatalogue.ui.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizky.submissionthreemoviecatalogue.R
import kotlinx.android.synthetic.main.item_row_movie.view.btn_detail
import kotlinx.android.synthetic.main.item_row_tvshow.view.*

class TvshowAdapter : RecyclerView.Adapter<TvshowAdapter.TvshowViewHolder>() {
    private val mData = ArrayList<TvshowItems>()
    fun setData(items: ArrayList<TvshowItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): TvshowViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_tvshow, viewGroup, false)
        return TvshowViewHolder(mView)
    }

    override fun onBindViewHolder(tvshowViewHolder: TvshowViewHolder, position: Int) {
        tvshowViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class TvshowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvshowItems: TvshowItems) {
            with(itemView) {
                val poster = tvshowItems.poster_path
                val posterLink = "https://image.tmdb.org/t/p/original$poster"
                Glide.with(itemView.context)
                    .load(posterLink)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_tvshow_poster)
                tv_tvshow_name.text = tvshowItems.name
                tv_tvshow_overview.text = tvshowItems.overview

                btn_detail.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Detail For ${tvshowItems.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}