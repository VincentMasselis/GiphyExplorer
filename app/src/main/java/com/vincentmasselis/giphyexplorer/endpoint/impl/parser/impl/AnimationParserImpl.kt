package com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl

import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.domain.Rating
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.AnimationParser
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.DateParser
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.ImagesParser
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.UserParser
import javax.inject.Inject

class AnimationParserImpl @Inject constructor(
    private val userParser: UserParser,
    private val dateParser: DateParser,
    private val imagesParser: ImagesParser
) : AnimationParser {
    override fun parse(jsonObject: JsonObject): Animation? = jsonObject.run {
        obj("images")!!.let { imagesParser.parse(it) }?.let { images ->
            Animation(
                string("type")!!,
                string("id")!!,
                string("title")!!,
                string("slug")!!,
                string("url")!!,
                string("bitly_url")!!,
                string("embed_url")!!,
                string("username")!!,
                string("source")!!,
                string("rating")!!.let { stringRating -> Rating.values().first { it.mpaaStyle.equals(stringRating, true) } },
                obj("user")?.let { userParser.parse(it) },
                string("source_tld")!!,
                string("source_post_url")!!,
                string("update_datetime")?.let { dateParser.parse(it) },
                string("create_datetime")?.let { dateParser.parse(it) },
                string("import_datetime")!!.let { dateParser.parse(it) },
                string("trending_datetime")?.let { dateParser.parse(it) },
                images
            )
        }
    }
}