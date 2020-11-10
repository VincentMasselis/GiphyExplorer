package com.vincentmasselis.giphyexplorer.endpoint.impl.parser

import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.Images

interface ImagesParser {
    fun parse(jsonObject: JsonObject): Images?
}