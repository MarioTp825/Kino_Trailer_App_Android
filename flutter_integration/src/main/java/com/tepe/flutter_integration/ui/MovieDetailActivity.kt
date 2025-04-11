package com.tepe.flutter_integration.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.tepe.domain.model.movie.MovieUI
import com.tepe.flutter_integration.contracts.FlutterBridge
import com.tepe.flutter_integration.contracts.MovieParams
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity: AppCompatActivity() {
    private val gson: Gson = Gson()

    @Inject
    lateinit var flutterBridge: FlutterBridge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = intent.getSerializableExtra(MOVIE_PARAM) as? MovieUI

        if (movie == null) {
            finish()
            return
        }

        //Double check engine
        flutterBridge.initEngine(this)

        val view = buildFlutterView(movie)
        if(view == null) {
            finish()
            return
        }

        setContentView(view)
    }

    private fun buildFlutterView(movie:MovieUI): View? {
        val data = gson.toJson(movie)
        flutterBridge.sendData(data, MovieParams.MovieDetail)

        return flutterBridge.getView(this)
    }

    companion object {
        private const val MOVIE_PARAM = "movie_param"

        fun buildIntent(movie: MovieUI, activity: Activity): Intent {
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_PARAM, movie)
            return intent
        }
    }
}