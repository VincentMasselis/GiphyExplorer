package com.vincentmasselis.giphyexplorer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.vincentmasselis.giphyexplorer.ioc.DaggerEndpointComponent
import com.vincentmasselis.giphyexplorer.ioc.DaggerRootComponent
import com.vincentmasselis.giphyexplorer.ioc.RootComponent
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Future

private lateinit var privateApplicationContext: Context
val applicationContext get() = privateApplicationContext

private lateinit var privateRootComponent: Future<RootComponent>
val rootComponent: RootComponent get() = privateRootComponent.get()

internal class RootComponentInitializer : ContentProvider() {

    override fun onCreate(): Boolean {
        // This code is placed here instead of [GiphyExplorerApplication] because a ContentProvider is initialized before an application object
        privateApplicationContext = context!!.applicationContext

        privateRootComponent = Single.fromCallable { DaggerRootComponent.factory().build(DaggerEndpointComponent.factory().build()) }
            .subscribeOn(Schedulers.computation())
            .toFuture()
        return true
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
}