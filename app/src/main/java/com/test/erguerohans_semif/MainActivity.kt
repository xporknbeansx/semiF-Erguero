package com.test.erguerohans_semif


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var tweetApiService: TweetApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://eldroid.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        tweetApiService = retrofit.create(TweetApiService::class.java)

        // Initialize UI elements
        val lastNameInput: EditText = findViewById(R.id.lastNameInput)
        val tweetIdInput: EditText = findViewById(R.id.tweetIdInput)
        val nameInput: EditText = findViewById(R.id.nameInput)
        val descriptionInput: EditText = findViewById(R.id.descriptionInput)
        val textViewResult: TextView = findViewById(R.id.textViewResult)

        // Button for reading all tweets
        val buttonReadAll: Button = findViewById(R.id.buttonReadAll)
        buttonReadAll.setOnClickListener {
            // Logic to read all tweets
            textViewResult.text = "All tweets displayed"
        }

        // Button for reading a specific tweet
        val buttonReadSpecific: Button = findViewById(R.id.buttonReadSpecific)
        buttonReadSpecific.setOnClickListener {
            // Logic to read a specific tweet based on some identifier
            textViewResult.text = "Specific tweet displayed"
        }

        // Button for creating a tweet
        val buttonCreate: Button = findViewById(R.id.buttonCreate)
        buttonCreate.setOnClickListener {
            val lastName = lastNameInput.text.toString()
            val tweetId = tweetIdInput.text.toString()
            val name = nameInput.text.toString()
            val description = descriptionInput.text.toString()

            val tweet = Tweet(lastName, tweetId, name, description)
            createTweet(tweet, textViewResult)
        }

        // Button for updating a tweet
        val buttonUpdate: Button = findViewById(R.id.buttonUpdate)
        buttonUpdate.setOnClickListener {
            val lastName = lastNameInput.text.toString()
            val tweetId = tweetIdInput.text.toString()
            val name = nameInput.text.toString()
            val description = descriptionInput.text.toString()

            val updatedTweet = Tweet(lastName, tweetId, name, description)
            updateTweet(tweetId, updatedTweet, textViewResult)
        }

        // Button for deleting a tweet
        val buttonDelete: Button = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            // Logic to delete a tweet
            textViewResult.text = "Tweet deleted"
        }
    }

    private fun createTweet(tweet: Tweet, textViewResult: TextView) {
        val call = tweetApiService.createTweet(tweet)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    textViewResult.text = "New tweet created"
                } else {
                    textViewResult.text = "Failed to create tweet: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                textViewResult.text = "Error: ${t.message}"
            }
        })
    }

    private fun updateTweet(tweetId: String, tweet: Tweet, textViewResult: TextView) {
        val call = tweetApiService.updateTweet(tweetId, tweet)

        call.enqueue(object : Callback<Tweet> {
            override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {
                if (response.isSuccessful) {
                    textViewResult.text = "Tweet updated successfully"
                } else {
                    textViewResult.text = "Failed to update tweet: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                textViewResult.text = "Error: ${t.message}"
            }
        })
    }


}