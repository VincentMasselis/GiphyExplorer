package com.vincentmasselis.giphyexplorer.endpoint.impl.parser.impl

import com.vincentmasselis.giphyexplorer.endpoint.impl.parser.DateParser
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateParserImpl @Inject constructor() : DateParser {

    companion object {
        private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    }

    override fun parse(stringValue: String): Date = dateFormatter.parse(stringValue)!!
}