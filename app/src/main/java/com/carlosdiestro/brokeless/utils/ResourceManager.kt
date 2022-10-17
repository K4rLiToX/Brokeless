package com.carlosdiestro.brokeless.utils

import androidx.annotation.DrawableRes
import com.carlosdiestro.brokeless.R

object ResourceManager {

    @DrawableRes
    fun getDrawableResource(value: String) : Int {
        return when(value) {
            "ic_franc" -> R.drawable.ic_franc
            "ic_euro" -> R.drawable.ic_euro
            "ic_dollar" -> R.drawable.ic_dollar
            "ic_pound" -> R.drawable.ic_pound
            else -> -1
        }
    }
}