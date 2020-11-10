package com.vincentmasselis.giphyexplorer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class FragmentDebugActivity : AppCompatActivity(R.layout.activity_fragment_debug) {
    companion object {
        fun intent(context: Context): Intent = Intent(context, FragmentDebugActivity::class.java)
    }
}