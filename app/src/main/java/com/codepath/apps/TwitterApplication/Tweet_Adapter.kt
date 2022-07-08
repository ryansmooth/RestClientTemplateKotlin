package com.codepath.apps.TwitterApplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.TwitterApplication.models.Tweet
import com.codepath.apps.twitterApplication.R


    class TweetAdapter (val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)

            val view = inflater.inflate(R.layout.item_tweet, parent,false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val tweet: Tweet = tweets.get(position)

            holder.TweetUsername.text= tweet.user?.name
            holder.Tweet.text = tweet.body

            Glide.with(holder.itemView).load(tweet.user?.publicImageURL).into(holder.imageView)
        }

        override fun getItemCount(): Int {
            return tweets.size
        }

            // Clean all elements of the recycler
            fun clear() {
                tweets.clear()
                notifyDataSetChanged()
            }

            // Add a list of items -- change to type used
            fun addAll(tweetList: List<Tweet>) {
                tweets.addAll(tweetList)
                notifyDataSetChanged()
            }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
            val TweetUsername = itemView.findViewById<TextView>(R.id.TweetUsername)
            val Tweet = itemView.findViewById<TextView>(R.id.Tweet)
        }

    }