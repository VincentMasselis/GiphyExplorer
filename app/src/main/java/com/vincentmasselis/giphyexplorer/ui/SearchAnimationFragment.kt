package com.vincentmasselis.giphyexplorer.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vincentmasselis.giphyexplorer.R
import com.vincentmasselis.giphyexplorer.databinding.FragmentSearchAnimationBinding
import com.vincentmasselis.giphyexplorer.domain.Animation
import com.vincentmasselis.giphyexplorer.endpoint.impl.HttpCodeException
import com.vincentmasselis.giphyexplorer.endpoint.impl.HttpIOException
import com.vincentmasselis.giphyexplorer.rootComponent
import com.vincentmasselis.giphyexplorer.shaper.SearchAnimationShaper
import com.vincentmasselis.giphyexplorer.ui.utils.dpToPx
import com.vincentmasselis.giphyexplorer.ui.utils.viewBinding
import com.vincentmasselis.rxuikotlin.LifecycleAdapter
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxuikotlin.subscribe
import com.vincentmasselis.rxuikotlin.utils.FragmentState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.round

/**
 * Create this fragment to display a list of [Animation] depending to the text filled by [inputString].
 * Host activity must implement [TextInputHolder] when using this fragment.
 */
class SearchAnimationFragment : Fragment(R.layout.fragment_search_animation) {
    companion object {
        fun builder() = SearchAnimationFragment()
    }

    @Inject lateinit var searchShaper: SearchAnimationShaper
    @Inject lateinit var inputString: Observable<String>

    private val binding by viewBinding(FragmentSearchAnimationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootComponent.searchFactory.build((requireActivity() as TextInputHolder).testInputObs.map { it.toString() })
            .also { it.inject(this) }

        //allow to display more columns on tablet or in landscape mode /360 * 2 allow pair span count only
        val spanCount = round(Resources.getSystem().displayMetrics.widthPixels / 360.dpToPx().toDouble()).toInt() * 2
        binding.fragmentSearchAnimationRecyclerView.layoutManager =
            StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        binding.fragmentSearchAnimationRecyclerView.subscribe(adapter).disposeOnState(FragmentState.DESTROY_VIEW, this)

        inputString
            .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe { adapter.clear() }
            .disposeOnState(FragmentState.DESTROY_VIEW, this)
    }

    private val adapter by lazy {
        object : LifecycleAdapter<AnimationCell>() {

            private val animations = mutableListOf<Animation>()

            init {
                searchShaper.list()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            animations.clear()
                            animations.addAll(it)
                            notifyDataSetChanged()
                        },
                        {
                            AlertDialog.Builder(requireActivity())
                                .setTitle("Failed to load the results")
                                .setMessage(
                                    when (it) {
                                        is HttpIOException -> "Check your internet connection"
                                        is HttpCodeException -> "Check your API key"
                                        else -> {
                                            Log.e(MainActivity.TAG, "", it)
                                            "Unknown error please retry later"
                                        }
                                    }
                                )
                                .setNeutralButton("Ok") { _, _ -> }
                                .show()
                        }
                    )
                    .disposeOnState(FragmentState.DESTROY_VIEW, this@SearchAnimationFragment)
            }

            fun clear() {
                val size = animations.size
                animations.clear()
                notifyItemRangeRemoved(0, size)
            }

            override fun getItemCount(): Int = animations.size

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimationCell = AnimationCell(parent)

            override fun onBindViewHolder(holder: AnimationCell, position: Int) = holder.bind(animations[position])
        }
    }
}