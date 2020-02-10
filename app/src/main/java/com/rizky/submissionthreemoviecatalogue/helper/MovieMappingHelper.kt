package com.rizky.submissionthreemoviecatalogue.helper

import android.database.Cursor
import com.rizky.submissionthreemoviecatalogue.db.DatabaseContract
import com.rizky.submissionthreemoviecatalogue.ui.movie.MovieItems

object MovieMappingHelper {

    fun mapCursorToArrayList(movieFavoriteCursor: Cursor): ArrayList<MovieItems> {
        val movieFavoriteList = ArrayList<MovieItems>()
        while (movieFavoriteCursor.moveToNext()) {
            val id = movieFavoriteCursor.getInt(
                movieFavoriteCursor.getColumnIndexOrThrow(
                    DatabaseContract.MovieFavoriteColumns._ID
                )
            )
            val title = movieFavoriteCursor.getString(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.TITLE)
            )
            val release_date = movieFavoriteCursor.getString(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.RELEASE_DATE)
            )
            val overview = movieFavoriteCursor.getString(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.OVERVIEW)
            )
            val poster_path = movieFavoriteCursor.getString(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.POSTER_PATH)
            )
            val vote_count = movieFavoriteCursor.getInt(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.VOTE_COUNT)
            )
            val popularity = movieFavoriteCursor.getInt(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.POPULARITY)
            )
            val backdrop_path = movieFavoriteCursor.getString(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.BACKDROP_PATH)
            )
            val vote_average = movieFavoriteCursor.getInt(
                movieFavoriteCursor.getColumnIndexOrThrow(DatabaseContract.MovieFavoriteColumns.VOTE_AVERAGE)
            )
            movieFavoriteList.add(
                MovieItems(
                    id,
                    title,
                    release_date,
                    overview,
                    poster_path,
                    vote_count,
                    popularity,
                    backdrop_path,
                    vote_average
                )
            )
        }
        return movieFavoriteList
    }
}