package com.vincentmasselis.giphyexplorer.endpoint.impl.parser

import java.util.*

interface DateParser {
    fun parse(stringValue: String): Date
}