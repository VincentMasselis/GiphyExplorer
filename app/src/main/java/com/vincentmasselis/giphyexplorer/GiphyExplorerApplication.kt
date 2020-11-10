package com.vincentmasselis.giphyexplorer

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.subjects.BehaviorSubject

private val appIsInForegroundSubject = BehaviorSubject.create<Boolean>()
val appIsInForegroundObs: Observable<Boolean> get() = appIsInForegroundSubject.hide()

class GiphyExplorerApplication : Application() {

    companion object {
        private const val TAG = "GiphyExplorerApplication"
    }

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler { Log.e(TAG, "Unhandled error occurred", it) }

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            private var activityReferences = 0
            private var isActivityChangingConfigurations = false
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {
                if (++activityReferences == 1 && isActivityChangingConfigurations.not())
                    appIsInForegroundSubject.onNext(true)
            }

            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {
                isActivityChangingConfigurations = activity.isChangingConfigurations
                if (--activityReferences == 0 && isActivityChangingConfigurations.not())
                    appIsInForegroundSubject.onNext(false)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}