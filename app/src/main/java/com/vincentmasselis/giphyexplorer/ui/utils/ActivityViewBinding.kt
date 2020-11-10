package com.vincentmasselis.giphyexplorer.ui.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Activity.viewBinding(binder: (View) -> T): ReadOnlyProperty<Activity, T> = ActivityViewBinding(this, binder)

private class ActivityViewBinding<T : ViewBinding>(activity: Activity, binder: (View) -> T) : ReadOnlyProperty<Activity, T> {

    private var host: Pair<Activity, (View) -> T>? = activity to binder
    private val lock = ReentrantLock()
    @Volatile private var binding: T? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        binding?.also { return it }

        return lock.withLock {
            binding
                ?: host!!.second(host!!.first.requireContentView()).also {
                    binding = it
                    host = null
                }
        }
    }

    private fun Activity.requireContentView(): View = checkNotNull(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) {
        "Call setContentView() or use FragmentActivity's secondary constructor passing layout res id."
    }
}
