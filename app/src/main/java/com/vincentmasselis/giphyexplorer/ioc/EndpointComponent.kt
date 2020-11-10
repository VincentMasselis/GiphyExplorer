package com.vincentmasselis.giphyexplorer.ioc

import com.vincentmasselis.giphyexplorer.endpoint.RandomEndpoint
import com.vincentmasselis.giphyexplorer.endpoint.SearchEndpoint
import dagger.Component
import io.reactivex.rxjava3.core.Observable
import javax.inject.Singleton


/**
 * By spiting [RootComponent] into 2 different [Component], I can decide which bindings from [EndpointComponent] are visible to
 * [RootComponent]. For instance, the parsers used by the the endpoints are not available for injection for the classes bound into
 * [RootComponent], it's a way to keep the usage of the parser at the place they belongs, close to the endpoint, not to the UI.
 */
@Singleton
@Component(modules = [EndpointModule::class])
interface EndpointComponent {
    @Component.Factory
    interface Factory {
        fun build(): EndpointComponent
    }

    fun onRequestDuration(): Observable<Long>

    fun randomEndpoint(): RandomEndpoint

    fun searchEndpoint(): SearchEndpoint
}