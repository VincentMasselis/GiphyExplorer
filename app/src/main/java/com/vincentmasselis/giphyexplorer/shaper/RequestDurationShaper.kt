package com.vincentmasselis.giphyexplorer.shaper

import io.reactivex.rxjava3.core.Observable

interface RequestDurationShaper {
    /**
     * Returns the average duration for every request send to the backend, it only reads the last values and the computation is not
     * necessary an average but a combination of multiple methods.
     *
     * Returns the result in second, emits the last value at subscription but not could emit at all if not enough request duration were
     * computed.
     */
    fun averageDuration(): Observable<Double>
}