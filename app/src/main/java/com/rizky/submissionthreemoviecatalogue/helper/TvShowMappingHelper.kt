package com.rizky.submissionthreemoviecatalogue.helper

import android.database.Cursor
import com.rizky.submissionthreemoviecatalogue.db.DatabaseContract
import com.rizky.submissionthreemoviecatalogue.ui.tvshow.TvshowItems

object TvShowMappingHelper {

    fun mapCursorToArrayList(tvShowFavoriteCursor: Cursor): ArrayList<TvshowItems> {
        val tvShowFavoriteList = ArrayList<TvshowItems>()

        while (tvShowFavoriteCursor.moveToNext()) {
            val id = tvShowFavoriteCursor.getInt(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns._ID)
            )
            val name = tvShowFavoriteCursor.getString(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.NAME)
            )
            val first_air_date = tvShowFavoriteCursor.getString(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.FIRST_AIR_DATE)
            )
            val overview = tvShowFavoriteCursor.getString(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.OVERVIEW)
            )
            val poster_path = tvShowFavoriteCursor.getString(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.POSTER_PATH)
            )
            val backdrop_path = tvShowFavoriteCursor.getString(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.BACKDROP_PATH)
            )
            val vote_count = tvShowFavoriteCursor.getInt(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.VOTE_COUNT)
            )
            val popularity = tvShowFavoriteCursor.getInt(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.POPULARITY)
            )
            val vote_average = tvShowFavoriteCursor.getInt(
                tvShowFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.TvShowFavoriteColumns.VOTE_AVERAGE)
            )
            tvShowFavoriteList.add(
                TvshowItems(
                    id,
                    name,
                    first_air_date,
                    overview,
                    poster_path,
                    backdrop_path,
                    vote_count,
                    popularity,
                    vote_average
                )
            )
        }
        return tvShowFavoriteList
    }
}