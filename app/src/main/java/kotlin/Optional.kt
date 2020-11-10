/**
 * DO NOT CHANGE THIS FILE'S LOCATION !!
 *
 * IntelliJ doesn't automatically import this extension component1() if component1() is declared under the "normal" package
 * `com.vincentmasselis.giphyexplorer`. The only way to automatically import is to put this extension into a package which is always imported,
 * `kotlin` is one of them.
 */
package kotlin

import java.util.*

operator fun <T> Optional<T>.component1(): T? = orElse(null)