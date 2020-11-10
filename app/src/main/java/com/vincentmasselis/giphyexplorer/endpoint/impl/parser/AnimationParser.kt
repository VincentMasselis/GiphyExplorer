package com.vincentmasselis.giphyexplorer.endpoint.impl.parser

import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.Animation

interface AnimationParser {
    fun parse(jsonObject: JsonObject): Animation?
}