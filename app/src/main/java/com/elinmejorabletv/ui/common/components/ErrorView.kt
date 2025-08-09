package com.elinmejorabletv.ui.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.elinmejorabletv.R

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val errorIcon: ImageView
    private val errorMessage: TextView
    private val retryButton: Button

    init {
        orientation = VERTICAL

        LayoutInflater.from(context).inflate(R.layout.view_error, this, true)

        errorIcon = findViewById(R.id.ivError)
        errorMessage = findViewById(R.id.tvErrorMessage)
        retryButton = findViewById(R.id.btnRetry)
    }

    fun setErrorMessage(message: String) {
        errorMessage.text = message
    }

    fun setRetryAction(action: () -> Unit) {
        retryButton.setOnClickListener { action() }
    }

    fun setRetryButtonVisibility(isVisible: Boolean) {
        retryButton.visibility = if (isVisible) VISIBLE else GONE
    }
}