package com.elinmejorabletv.ui.common.extensions

import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation

fun ImageView.loadImageCircular(url: String?) {
    load(url) {
        crossfade(true)
        diskCachePolicy(CachePolicy.ENABLED)
        memoryCachePolicy(CachePolicy.ENABLED)
        transformations(CircleCropTransformation())
        placeholder(com.elinmejorabletv.R.drawable.placeholder_track)
        error(com.elinmejorabletv.R.drawable.placeholder_track)
    }
}

fun ImageView.loadImage(url: String?) {
    load(url) {
        crossfade(true)
        diskCachePolicy(CachePolicy.ENABLED)
        memoryCachePolicy(CachePolicy.ENABLED)
        placeholder(com.elinmejorabletv.R.drawable.placeholder_track)
        error(com.elinmejorabletv.R.drawable.placeholder_track)
    }
}