package com.tepe.mymovie.utils.navType

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.tepe.domain.model.movie.MovieUI
import kotlinx.serialization.json.Json

val MovieUINavType = object : NavType<MovieUI>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): MovieUI? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): MovieUI {
        val uri = Uri.decode(value)
        return Json.decodeFromString(uri)
    }

    override fun put(bundle: Bundle, key: String, value: MovieUI) {
        val json = Json.encodeToString(value)
        bundle.putString(key, json)
    }

    override fun serializeAsValue(value: MovieUI): String {
        val json = Json.encodeToString(value)
        return Uri.encode(json)
    }
}