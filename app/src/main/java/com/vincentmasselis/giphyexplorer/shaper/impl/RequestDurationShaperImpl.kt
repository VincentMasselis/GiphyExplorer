package com.vincentmasselis.giphyexplorer.shaper.impl

import com.vincentmasselis.giphyexplorer.ioc.SingleInstance
import com.vincentmasselis.giphyexplorer.shaper.RequestDurationShaper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@SingleInstance
class RequestDurationShaperImpl @Inject constructor(onRequestDuration: Observable<Long>) : RequestDurationShaper {

    private val averageDurationSubject = BehaviorSubject.create<Double>()

    init {
        onRequestDuration
            .observeOn(Schedulers.single()) // There is a bug with this buffer operator which raise some ConcurrentModificationException
            .buffer(5, 1)
            .map { durations ->
                durations.sort()
                durations.removeFirst()
                durations.removeLast()
                durations.average().div(1000)
            }
            .subscribe { averageDurationSubject.onNext(it) }
    }

    override fun averageDuration(): Observable<Double> = averageDurationSubject.hide()!!
}