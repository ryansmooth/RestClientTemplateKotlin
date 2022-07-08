package com.codepath.apps.TwitterApplication

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.TwitterApplication.models.Tweet
import com.codepath.apps.twitterApplication.R
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class TimelineActivity {

    class TimelineActivity : AppCompatActivity() {

        lateinit var client: TwitterClient

        lateinit var rvTweets: RecyclerView

        lateinit var adapter: TweetAdapter

        lateinit var swipeContainer: SwipeRefreshLayout

        val tweets = ArrayList<Tweet>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_timeline)

            client = TwitterApp.getRestClient(this)

            swipeContainer = findViewById(R.id.swipeContainer)

            swipeContainer.setOnRefreshListener {
                Log.i(TAG, "Refresh timeline")
                populateHomeTimeline()
            }

            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )

            rvTweets = findViewById(R.id.rvTweets)
            adapter = TweetAdapter(tweets)

            rvTweets.layoutManager = LinearLayoutManager(this)
            rvTweets.adapter = adapter

            populateHomeTimeline()
        }

        fun populateHomeTimeline() {
            client.getHomeTimeline(object : JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.i(ContentValues.TAG, "OnFailure $statusCode")
                }

                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {

                    val jsonArray = json.jsonArray

                    try {
                        //Clear out tweets
                        adapter.clear()
                        val listOfNewTweetsRetrieved = Tweet.fromJsonArray(jsonArray)
                        tweets.addAll(listOfNewTweetsRetrieved)
                        adapter.notifyDataSetChanged()
                        swipeContainer.setRefreshing(false)
                    } catch (e: JSONException) {
                        Log.i(ContentValues.TAG, "OnSuccess $json")
                    }
                }
            })
        }
    }
}