package kotlin

@DslMarker
annotation class ContextDslAllInclusive

/**
 * Used to force the when keyword to implement every case of a enum, sealed class, int and long.
 *
 * Similar to `when {}.exhaustive()` but this variant permits to write as a prefix instead of a suffix. It's written this way :
 * ```
 * all inclusive when(it) {
 *       // Your cases
 * }
 * ```
 *
 * @see https://www.avoir-alire.com/IMG/arton39868.jpg
 */
@Suppress("UNUSED_PARAMETER", "ClassName")
@ContextDslAllInclusive
object all {
    @ContextDslAllInclusive
    infix fun <T> inclusive(that: T) {
    }
}