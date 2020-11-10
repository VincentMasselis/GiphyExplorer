package com.vincentmasselis.giphyexplorer.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.vincentmasselis.giphyexplorer.R
import com.vincentmasselis.giphyexplorer.databinding.ActivityMainBinding
import com.vincentmasselis.giphyexplorer.endpoint.impl.HttpCodeException
import com.vincentmasselis.giphyexplorer.endpoint.impl.HttpIOException
import com.vincentmasselis.giphyexplorer.rootComponent
import com.vincentmasselis.giphyexplorer.shaper.RandomAnimationShaper
import com.vincentmasselis.giphyexplorer.ui.utils.viewBinding
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxuikotlin.utils.ActivityState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/** Loads and display a random animation when started */
class MainActivity : AppCompatActivity(R.layout.activity_main), TextInputHolder {

    companion object {
        const val TAG = "MainActivity"
    }


    @Inject lateinit var randomAnimationShaper: RandomAnimationShaper

    private val binding by viewBinding(ActivityMainBinding::bind)
    override val testInputObs: Observable<CharSequence> by lazy { binding.activityMainSearchEditText.textChanges() }

    override fun onCreate(savedInstanceState: Bundle?) {
        rootComponent.inject(this)
        super.onCreate(savedInstanceState)

        testInputObs
            .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isBlank()) binding.activityMainMotionLayout.transitionToStart()
                else binding.activityMainMotionLayout.transitionToEnd()
            }
            .disposeOnState(ActivityState.DESTROY, this)

        binding.activityMainSearchEditTextCancel.clicks()
            .subscribe {
                binding.activityMainSearchEditText.text = null
                binding.activityMainMotionLayout.transitionToStart()
                getSystemService<InputMethodManager>()!!.hideSoftInputFromWindow(binding.activityMainSearchEditText.windowToken, 0)
            }
            .disposeOnState(ActivityState.DESTROY, this)
    }

    override fun onStart() {
        super.onStart()
        Single
            .defer {
                supportFragmentManager.findFragmentById(R.id.activity_main_random_container)
                    ?.let { Single.just(it as AnimationViewerFragment) }
                    ?: randomAnimationShaper.animation()
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { animation ->
                            AnimationViewerFragment.builder(animation).also {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.activity_main_random_container, it)
                                    .commitNow()
                            }
                        }
            }
            .flatMapObservable { fragment -> fragment?.animationDisplayedObs ?: Observable.empty() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                binding.activityMainLoadingView.visibility = View.VISIBLE
                binding.activityMainLoadingView.playAnimation()
            }
            .subscribe({ animationDisplayed ->
                when {
                    animationDisplayed.not() && binding.activityMainLoadingView.visibility == View.GONE -> {
                        binding.activityMainLoadingView.visibility = View.VISIBLE
                        binding.activityMainLoadingView.playAnimation()
                    }
                    animationDisplayed && binding.activityMainLoadingView.visibility == View.VISIBLE -> {
                        binding.activityMainLoadingView.visibility = View.GONE
                        binding.activityMainLoadingView.cancelAnimation()
                    }
                }
            }, {
                if (binding.activityMainLoadingView.visibility == View.VISIBLE) {
                    binding.activityMainLoadingView.visibility = View.GONE
                    binding.activityMainLoadingView.cancelAnimation()
                }
                AlertDialog.Builder(this)
                    .setTitle("Failed to load the welcome gif")
                    .setMessage(
                        when (it) {
                            is HttpIOException -> "Check your internet connection"
                            is HttpCodeException -> "Check your API key"
                            else -> {
                                Log.e(TAG, "", it)
                                "Unknown error please retry later"
                            }
                        }
                    )
                    .setNeutralButton("Ok") { _, _ -> }
                    .show()
            })
            .disposeOnState(ActivityState.DESTROY, this)

        if (supportFragmentManager.findFragmentById(R.id.activity_main_search_result_container) == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_search_result_container, SearchAnimationFragment.builder())
                .commitNow()
    }

    override fun onBackPressed() {
        if (binding.activityMainMotionLayout.progress == 1f) {
            binding.activityMainSearchEditText.text = null
            binding.activityMainMotionLayout.transitionToStart()
        } else super.onBackPressed()
    }
}