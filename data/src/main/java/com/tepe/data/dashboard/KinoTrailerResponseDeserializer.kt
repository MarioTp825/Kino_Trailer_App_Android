package com.tepe.data.dashboard

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.tepe.data.dashboard.responses.KinoTrailerResponse
import com.tepe.data.dashboard.responses.Movies
import com.tepe.data.dashboard.responses.Metadata
import java.lang.reflect.Type

fun createGsonKinoTrailer(): Gson {
    return GsonBuilder()
        .registerTypeAdapter(KinoTrailerResponse::class.java, KinoTrailerResponseDeserializer())
        .create()
}

class KinoTrailerResponseDeserializer : JsonDeserializer<KinoTrailerResponse> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): KinoTrailerResponse {
        val jsonObject = json.asJsonObject
        val moviesMap = mutableMapOf<String, Movies>()
        val metadata = context.deserialize<Metadata>(jsonObject.get("_metadata"), Metadata::class.java)

        // Process all properties that are not "_metadata"
        for (entry in jsonObject.entrySet()) {
            if (entry.key != "_metadata") {
                // This is a movie entry
                val movie = context.deserialize<Movies>(entry.value, Movies::class.java)
                moviesMap[entry.key] = movie
            }
        }

        return KinoTrailerResponse(moviesMap, metadata)
    }
}