package com.vincentmasselis.giphyexplorer.endpoint.impl.parser

import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.User

interface UserParser {
    fun parse(jsonObject: JsonObject): User
}