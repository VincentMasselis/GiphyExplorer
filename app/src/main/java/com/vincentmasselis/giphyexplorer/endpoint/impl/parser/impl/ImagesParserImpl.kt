package com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl

import android.util.Log
import com.beust.klaxon.JsonObject
import com.vincentmasselis.giphyexplorer.domain.*
import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.ImagesParser
import javax.inject.Inject

class ImagesParserImpl @Inject constructor() : ImagesParser {

    companion object {
        const val TAG = "ImagesParserImpl"
    }

    override fun parse(jsonObject: JsonObject): Images? = try {
        Images(
            jsonObject.obj("fixed_height")!!.run {
                FixedHeight(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt(),
                    string("size")!!.toLong(),
                    string("mp4")!!,
                    string("mp4_size")!!.toLong(),
                    string("webp")!!,
                    string("webp_size")!!.toLong()
                )
            },
            jsonObject.obj("fixed_height_still")!!.run {
                FixedHeightStill(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("fixed_height_downsampled")!!.run {
                FixedHeightDownSampled(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt(),
                    string("size")!!.toLong(),
                    string("webp")!!,
                    string("webp_size")!!.toLong()
                )
            },
            jsonObject.obj("fixed_width")!!.run {
                FixedWidth(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt(),
                    string("size")!!.toLong(),
                    string("mp4")!!,
                    string("mp4_size")!!.toLong(),
                    string("webp")!!,
                    string("webp_size")!!.toLong()
                )
            },
            jsonObject.obj("fixed_width_still")!!.run {
                FixedWidthStill(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("fixed_width_downsampled")!!.run {
                FixedWidthDownSampled(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt(),
                    string("size")!!.toLong(),
                    string("webp")!!,
                    string("webp_size")!!.toLong()
                )
            },
            jsonObject.obj("fixed_height_small")!!.run {
                FixedHeightSmall(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt(),
                    string("size")!!.toLong(),
                    string("mp4")!!,
                    string("mp4_size")!!.toLong(),
                    string("webp")!!,
                    string("webp_size")!!.toLong()
                )
            },
            jsonObject.obj("fixed_height_small_still")!!.run {
                FixedHeightSmallStill(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("fixed_width_small")!!.run {
                FixedWidthSmall(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt(),
                    string("size")!!.toLong(),
                    string("mp4")!!,
                    string("mp4_size")!!.toLong(),
                    string("webp")!!,
                    string("webp_size")!!.toLong()
                )
            },
            jsonObject.obj("fixed_width_small_still")!!.run {
                FixedWidthSmallStill(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("downsized")!!.run {
                Downsized(
                    string("url")!!,
                    string("size")!!.toLong(),
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("downsized_still")!!.run {
                DownsizedStill(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("downsized_large")!!.run {
                DownsizedLarge(
                    string("url")!!,
                    string("size")!!.toLong(),
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("downsized_medium")!!.run {
                DownsizedMedium(
                    string("url")!!,
                    string("size")!!.toLong(),
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("downsized_small")!!.run {
                DownsizedSmall(
                    string("mp4")!!,
                    string("mp4_size")!!.toLong(),
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("original")!!.run {
                Original(
                    string("width")!!.toInt(),
                    string("height")!!.toInt(),
                    string("size")!!.toLong(),
                    string("frames")!!.toInt(),
                    string("mp4")!!,
                    string("mp4_size")!!.toLong(),
                    string("webp")!!,
                    string("webp_size")!!.toLong()
                )
            },
            jsonObject.obj("original_still")!!.run {
                OriginalStill(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            Looping(jsonObject.obj("looping")!!.string("mp4")!!),
            jsonObject.obj("preview")!!.run {
                PreviewMp4(
                    string("mp4")!!,
                    string("mp4_size")!!.toLong(),
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            },
            jsonObject.obj("preview_gif")!!.run {
                PreviewGif(
                    string("url")!!,
                    string("width")!!.toInt(),
                    string("height")!!.toInt()
                )
            }
        )
    } catch (e: Exception) {
        Log.w(TAG, "Returned Images object doesn't match the specs", e)
        null
    }
}