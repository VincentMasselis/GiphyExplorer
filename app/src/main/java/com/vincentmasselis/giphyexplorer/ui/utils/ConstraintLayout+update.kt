package com.vincentmasselis.giphyexplorer.ui.utils

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

inline fun ConstraintLayout.updateConstraints(updater: ConstraintSet.() -> Unit) {
    val newConstraints = ConstraintSet()
    newConstraints.clone(this)
    newConstraints.updater()
    newConstraints.applyTo(this)
}