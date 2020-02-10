package com.rizky.submissionthreemoviecatalogue.ui.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItems(
    var id: Int = 0,
    var title: String? = null,
    var release_date: String? = null,
    var overview: String? = null,
    var poster_path: String? = null,
    var vote_count: Int? = null,
    var popularity: Int = 0,
    var backdrop_path: String? = null,
    var vote_average: Int? = 0
) : Parcelable