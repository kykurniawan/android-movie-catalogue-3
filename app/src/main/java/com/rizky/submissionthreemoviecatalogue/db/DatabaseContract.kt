package com.rizky.submissionthreemoviecatalogue.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class MovieFavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME_MOVIE = "moviefavorite"
            const val _ID = "_id"
            const val TITLE = "title"
            const val RELEASE_DATE = "release_date"
            const val OVERVIEW = "overview"
            const val POSTER_PATH = "poster_path"
            const val VOTE_COUNT = "vote_count"
            const val POPULARITY = "popularity"
            const val BACKDROP_PATH = "backdrop_path"
            const val VOTE_AVERAGE = "vote_average"
        }
    }

    internal class TvShowFavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME_TVSHOW = "tvshowfavorite"
            const val _ID = "_id"
            const val NAME = "name"
            const val FIRST_AIR_DATE = "first_air_date"
            const val OVERVIEW = "overview"
            const val POSTER_PATH = "poster_path"
            const val BACKDROP_PATH = "backdrop_path"
            const val VOTE_COUNT = "vote_count"
            const val POPULARITY = "popularity"
            const val VOTE_AVERAGE = "vote_average"
        }
    }
}