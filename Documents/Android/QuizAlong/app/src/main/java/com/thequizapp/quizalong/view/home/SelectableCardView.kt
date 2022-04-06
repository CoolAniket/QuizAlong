package com.thequizapp.quizalong.view.home

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class SelectableCardView : MaterialCardView {
    @ColorInt
    private var unselectedBackgroundColor = 0

    @ColorInt
    private var selectedBackgroundColor = 0

    constructor(context: Context?) : super(context) {
        unselectedBackgroundColor = cardBackgroundColor.defaultColor
        setCardBackgroundColor(ColorStateList(arrayOf(intArrayOf(R.attr.state_selected), IntArray(0)), intArrayOf(selectedBackgroundColor, unselectedBackgroundColor)))
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        unselectedBackgroundColor = cardBackgroundColor.defaultColor
        setCardBackgroundColor(ColorStateList(arrayOf(intArrayOf(R.attr.state_selected), IntArray(0)), intArrayOf(selectedBackgroundColor, unselectedBackgroundColor)))
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        unselectedBackgroundColor = cardBackgroundColor.defaultColor
        setCardBackgroundColor(ColorStateList(arrayOf(intArrayOf(R.attr.state_selected), IntArray(0)), intArrayOf(selectedBackgroundColor, unselectedBackgroundColor)))
    }

    init {
        selectedBackgroundColor = ContextCompat.getColor(context, R.color.transparent)
    }
}