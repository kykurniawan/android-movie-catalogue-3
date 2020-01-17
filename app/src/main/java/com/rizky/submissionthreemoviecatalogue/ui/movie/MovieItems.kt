package com.rizky.submissionthreemoviecatalogue.ui.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieItems (
    var id: Int = 0,
    var title: String? = null,
    var release_date: String? = null,
    var overview: String? = null,
    var poster_path: String? = null,
    var vote_count: Int? = null,
    var popularity: Int = 0,
    var original_language: String? = null,
    var backdrop_path: String? = null
) : Parcelable