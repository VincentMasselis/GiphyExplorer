package com.vincentmasselis.giphyexplorer

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class ViewBindingTest {

    @Test
    fun fragmentReplace() {
        val rule = ActivityTestRule(FragmentDebugActivity::class.java)
        val activity = rule.launchActivity(FragmentDebugActivity.intent(getApplicationContext()))
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_debug_container, ViewBindingFragment1())
            .commit()
        sleep(500)
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_debug_container, ViewBindingFragment2())
            .commit()
        sleep(500)
    }

    @Test
    fun fragmentAddToBackStack() {
        val rule = ActivityTestRule(FragmentDebugActivity::class.java)
        val activity = rule.launchActivity(FragmentDebugActivity.intent(getApplicationContext()))
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_debug_container, ViewBindingFragment1(), "ViewBindingFragment1")
            .commit()
        sleep(500)
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.activity_fragment_debug_container, ViewBindingFragment2())
            .addToBackStack(null)
            .commit()
        sleep(500)
        activity.supportFragmentManager.findFragmentByTag("ViewBindingFragment1")!!.let { it as ViewBindingFragment1 }.also {
            try {
                it.binding.fragmentBlankTextView
                throw UnsupportedOperationException("An exception must be fire")
            } catch (e: IllegalStateException) {
                // Excepted exception
            }
        }
        sleep(500)
        activity.supportFragmentManager.popBackStack()
        sleep(500)
        val fragment1 = activity.supportFragmentManager.findFragmentByTag("ViewBindingFragment1") as ViewBindingFragment1
        rule.runOnUiThread { fragment1.binding.fragmentBlankTextView.text = "Hi after the backstack" }
        rule.finishActivity()
        sleep(5000) // When finishing the activity, the fragment manager takes a long time to destroy his fragments. Maybe to keep the animation clean, I don't known why.
        try {
            fragment1.binding.fragmentBlankTextView
        } catch (e: IllegalStateException) {
            // Excepted exception
        }
    }
}