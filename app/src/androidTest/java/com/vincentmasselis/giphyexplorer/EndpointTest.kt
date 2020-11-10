package com.vincentmasselis.giphyexplorer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vincentmasselis.giphyexplorer.ioc.DaggerEndpointComponent
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EndpointTest {
    @Test
    fun randomAnimation() {
        DaggerEndpointComponent.factory().build().randomEndpoint().randomAnimation()
            .test()
            .await()
            .assertComplete()
    }
}