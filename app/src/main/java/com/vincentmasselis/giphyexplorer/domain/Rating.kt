package com.vincentmasselis.giphyexplorer.domain

enum class Rating(val mpaaStyle: String) {
    YOUNG("Y"),
    GENERAL("G"),
    PARENTAL_GUIDANCE("PG"),
    PARENTAL_GUIDANCE_13("PG-13"),
    RESTRICTED("R")
}