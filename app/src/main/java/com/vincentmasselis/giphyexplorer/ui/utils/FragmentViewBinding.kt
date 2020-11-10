package com.vincentmasselis.giphyexplorer.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Fragment.viewBinding(binder: (View) -> T): ReadOnlyProperty<Fragment, T> = FragmentViewBinding(this, binder)

@Suppress("unused")
private class FragmentViewBinding<T : ViewBinding>(fragment: Fragment, binder: (View) -> T) :
    FragmentManager.FragmentLifecycleCallbacks(),
    ReadOnlyProperty<Fragment, T>,
    LifecycleObserver {

    private var host: Pair<Fragment, (View) -> T>? = fragment to binder
    private val lock = ReentrantLock()
    @Volatile private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        host!!.first.parentFragmentManager.registerFragmentLifecycleCallbacks(this, false)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.also { return it }

        return lock.withLock {
            val v1 = binding
            if (v1 != null) v1
            else {
                val h1 = host ?: throw IllegalStateException("Fragment is destroyed")
                h1.second(h1.first.requireView()).also { binding = it }
            }
        }
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        if (f === host!!.first) // I'm only observing the state changes for the current fragment
            binding = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        host!!.first.parentFragmentManager.unregisterFragmentLifecycleCallbacks(this)
        host!!.first.lifecycle.removeObserver(this)
        host = null
    }
}
