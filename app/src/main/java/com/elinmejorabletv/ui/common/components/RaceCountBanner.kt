package com.elinmejorabletv.ui.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.elinmejorabletv.R

class RaceCountBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val racesCountText: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_secondary_toolbar, this, true)

        racesCountText = findViewById(R.id.tvRacesCount)
    }

    fun setRacesCount(count: Int) {
        racesCountText.text = context.getString(R.string.today_races_count, count.toString())
    }
}