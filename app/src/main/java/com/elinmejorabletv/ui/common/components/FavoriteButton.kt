package com.elinmejorabletv.ui.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.elinmejorabletv.R
import com.elinmejorabletv.databinding.ViewFavoriteButtonBinding

class FavoriteButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewFavoriteButtonBinding

    private var _isFavorite: Boolean = false
    var isFavorite: Boolean
        get() = _isFavorite
        set(value) {
            _isFavorite = value
            updateButtonState()
        }

    var onFavoriteChanged: ((Boolean) -> Unit)? = null

    init {
        binding = ViewFavoriteButtonBinding.inflate(LayoutInflater.from(context), this)

        binding.favoriteIcon.setOnClickListener {
            isFavorite = !isFavorite
            onFavoriteChanged?.invoke(isFavorite)
        }

        updateButtonState()
    }

    private fun updateButtonState() {
        val iconRes = if (_isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
        val colorRes = if (_isFavorite) R.color.orange else R.color.medium_gray

        binding.favoriteIcon.setImageResource(iconRes)
        binding.favoriteIcon.setColorFilter(ContextCompat.getColor(context, colorRes))
    }
}