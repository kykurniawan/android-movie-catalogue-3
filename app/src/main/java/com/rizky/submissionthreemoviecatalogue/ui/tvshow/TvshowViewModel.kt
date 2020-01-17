package com.rizky.submissionthreemoviecatalogue.ui.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rizky.submissionthreemoviecatalogue.ui.tvshow.TvshowItems
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvshowViewModel : ViewModel() {

    companion object {
        private const val API_KEY = "ad17726d6285f718d265aa2de12213bb"
    }

    val listTvshow = MutableLiveData<ArrayList<TvshowItems>>()

    internal fun gettvshow(): LiveData<ArrayList<TvshowItems>> {
        return listTvshow
    }

    internal fun setTvshow() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<TvshowItems>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val tvshow = list.getJSONObject(i)
                        val tvshowItems = TvshowItems()

                        tvshowItems.id = tvshow.getInt("id")
                        tvshowItems.name = tvshow.getString("name")
                        tvshowItems.overview = tvshow.getString("overview")
                        tvshowItems.poster_path = tvshow.getString("poster_path")
                        tvshowItems.first_air_date = tvshow.getString("first_air_date")
                        tvshowItems.popularity = tvshow.getInt("popularity")
                        tvshowItems.original_language = tvshow.getString("original_language")
                        tvshowItems.vote_count = tvshow.getInt("vote_count")
                        listItems.add(tvshowItems)
                    }
                    listTvshow.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
}