package com.vincentmasselis.giphyexplorer.ui

import io.reactivex.rxjava3.core.Observable

interface TextInputHolder {
    val testInputObs: Observable<CharSequence>
}