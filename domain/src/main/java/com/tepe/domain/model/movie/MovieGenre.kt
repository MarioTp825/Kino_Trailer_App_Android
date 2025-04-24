package com.tepe.domain.model.movie

enum class MovieGenre(val urlValue: String) {
    Adventure("Adventure"),
    Drama("Drama"),
    Horror("Horror"),
    Comedy("Comedy"),
    Animation("Animation"),
    Family("Family"),
    ScienceFiction("Science Fiction"),
    Action("Action"),
    Thriller("Thriller"),
    Mystery("Mystery"),
    Crime("Crime"),
    Fantasy("Fantasy"),
    Romance("Romance"),
    Western("Western"),
    Documentary("Documentary"),
    History("History"),
    Music("Music"),
    War("War"),
    Foreign("Foreign"),
    TVMovie("TV Movie"),
    Biopic("Biopic"),
    SciFiFantasy("Sci-Fi & Fantasy"),
    ActionAdventure("Action & Adventure"),
    Sport("Sport"),
    Independent("Independent"),
    Kids("Kids"),
    RealityTV("Reality-TV"),
    Superhero("Superhero"),
    Main("Main Movies");

    companion object {
        fun fromUrlValue(urlValue: String): MovieGenre {
            return entries.firstOrNull { it.urlValue == urlValue } ?: Main
        }
    }
}