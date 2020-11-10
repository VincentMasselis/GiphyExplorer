package com.vincentmasselis.giphyexplorer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** a.k.a. [https://developers.giphy.com/docs/api/schema#image-object] */
@Parcelize
data class Images(
    /** Data on versions of this GIF with a fixed height of 200 pixels. Good for mobile use. */
    val fixedHeight: FixedHeight,
    /** Data on a static image of this GIF with a fixed height of 200 pixels. */
    val fixedHeightStill: FixedHeightStill,
    /** Data on versions of this GIF with a fixed height of 200 pixels and the number of frames reduced to 6. */
    val fixedHeightDownSampled: FixedHeightDownSampled,
    /** Data on versions of this GIF with a fixed width of 200 pixels. Good for mobile use. */
    val fixedWidth: FixedWidth,
    /** Data on a static image of this GIF with a fixed width of 200 pixels. */
    val fixedWidthStill: FixedWidthStill,
    /** Data on versions of this GIF with a fixed width of 200 pixels and the number of frames reduced to 6. */
    val fixedWidthDownSampled: FixedWidthDownSampled,
    /** Data on versions of this GIF with a fixed height of 100 pixels. Good for mobile keyboards. */
    val fixedHeightSmall: FixedHeightSmall,
    /** Data on a static image of this GIF with a fixed height of 100 pixels. */
    val fixedHeightSmallStill: FixedHeightSmallStill,
    /** Data on versions of this GIF with a fixed width of 100 pixels. Good for mobile keyboards. */
    val fixedWidthSmall: FixedWidthSmall,
    /** Data on a static image of this GIF with a fixed width of 100 pixels. */
    val fixedWidthSmallStill: FixedWidthSmallStill,
    /** Data on a version of this GIF downsized to be under 2mb. */
    val downsized: Downsized,
    /** Data on a static preview image of the downsized version of this GIF. */
    val downsizedStill: DownsizedStill,
    /** Data on a version of this GIF downsized to be under 8mb. */
    val downsizedLarge: DownsizedLarge,
    /** Data on a version of this GIF downsized to be under 5mb. */
    val downsizedMedium: DownsizedMedium,
    /** Data on a version of this GIF downsized to be under 200kb. */
    val downsizedSmall: DownsizedSmall,
    /** Data on the original version of this GIF. Good for desktop use. */
    val original: Original,
    /** Data on a static preview image of the original GIF. */
    val originalStill: OriginalStill,
    /** Data on the 15 second version of the GIF looping. */
    val looping: Looping,
    /** Data on a version of this GIF in .MP4 format limited to 50kb that displays the first 1-2 seconds of the GIF. */
    val previewMp4: PreviewMp4,
    /** Data on a version of this GIF limited to 50kb that displays the first 1-2 seconds of the GIF. */
    val previewGif: PreviewGif
) : Parcelable