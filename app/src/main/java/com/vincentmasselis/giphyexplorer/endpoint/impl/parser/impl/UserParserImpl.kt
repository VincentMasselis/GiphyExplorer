package com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl

import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.User
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.UserParser
import javax.inject.Inject

class UserParserImpl @Inject constructor() : UserParser {
    override fun parse(jsonObject: JsonObject): User = jsonObject.run {
        User(
            string("avatar_url")!!,
            string("banner_url")!!,
            string("profile_url")!!,
            string("username")!!,
            string("display_name")!!
        )
    }
}