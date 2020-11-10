package com.vincentmasselis.giphyexplorer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.vincentmasselis.giphyexplorer.ui.utils.updateConstraints

class ImageViewFrameDebugActivity : AppCompatActivity(R.layout.activity_debug) {

    companion object {
        private const val PARAM_HEIGHT = "PARAM_HEIGHT"
        private const val PARAM_WIDTH = "PARAM_WIDTH"
        fun intent(context: Context, width: Int, height: Int) = Intent(context, ImageViewFrameDebugActivity::class.java).apply {
            putExtra(PARAM_HEIGHT, height)
            putExtra(PARAM_WIDTH, width)
        }
    }

    private val constraintLayout by lazy { findViewById<ConstraintLayout>(R.id.activity_debug_container) }
    val imageView by lazy { findViewById<ImageView>(R.id.activity_debug_image_view)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        constraintLayout.updateConstraints {
            constrainWidth(R.id.activity_debug_image_view, intent.getIntExtra(PARAM_WIDTH, Int.MIN_VALUE))
            constrainHeight(R.id.activity_debug_image_view, intent.getIntExtra(PARAM_HEIGHT, Int.MIN_VALUE))
        }
    }
}