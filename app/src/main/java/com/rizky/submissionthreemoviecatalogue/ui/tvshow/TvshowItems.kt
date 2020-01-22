package com.rizky.submissionthreemoviecatalogue.ui.tvshow

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvshowItems (
    var id: Int = 0,
    var name: String? = null,
    var first_air_date: String? = null,
    var overview: String? = null,
    var poster_path: String? = null,
    var backdrop_path: String? = null,
    var vote_count: Int? = null,
    var popularity: Int = 0,
    var vote_average: Int? = 0
) : Parcelable