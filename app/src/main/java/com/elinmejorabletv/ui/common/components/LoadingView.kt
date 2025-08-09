package com.elinmejorabletv.ui.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.elinmejorabletv.R

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val progressBar: ProgressBar
    private val loadingMessage: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this, true)

        progressBar = findViewById(R.id.progressBar)
        loadingMessage = findViewById(R.id.tvLoadingMessage)
    }

    fun setMessage(message: String) {
        loadingMessage.text = message
    }
}