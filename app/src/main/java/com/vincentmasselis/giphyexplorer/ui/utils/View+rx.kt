package com.vincentmasselis.giphyexplorer.ui.utils

import android.view.View
import io.reactivex.rxjava3.core.Observable

interface Layout {
    val left: Int
    val top: Int
    val right: Int
    val bottom: Int
    val oldLeft: Int
    val oldTop: Int
    val oldRight: Int
    val oldBottom: Int
}

/** Rx wrapper for [View.addOnLayoutChangeListener] */
fun View.rxLayoutChangeListener() = Observable.create<Layout> { downStream ->
    val layout = object : Layout {
        override var left: Int = getLeft()
        override var top: Int = getTop()
        override var right: Int = getRight()
        override var bottom: Int = getBottom()
        override var oldLeft: Int = 0
        override var oldTop: Int = 0
        override var oldRight: Int = 0
        override var oldBottom: Int = 0
    }
    val listener = View.OnLayoutChangeListener { _, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
        layout.left = left
        layout.top = top
        layout.right = right
        layout.bottom = bottom
        layout.oldLeft = oldLeft
        layout.oldTop = oldTop
        layout.oldRight = oldRight
        layout.oldBottom = oldBottom
        downStream.onNext(layout)
    }
    downStream.setCancellable { removeOnLayoutChangeListener(listener) }
    addOnLayoutChangeListener(listener)
    downStream.onNext(layout) // Emit at sub
}!!