package com.carlosdiestro.brokeless.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.carlosdiestro.brokeless.R

object ResourceManager {

    @DrawableRes
    fun getDrawableResource(value: String): Int {
        return when (value) {
            "ic_franc"          -> R.drawable.ic_franc
            "ic_euro"           -> R.drawable.ic_euro
            "ic_dollar"         -> R.drawable.ic_dollar
            "ic_pound"          -> R.drawable.ic_pound
            "ic_job"            -> R.drawable.ic_job
            "ic_house"          -> R.drawable.ic_house
            "ic_groceries"      -> R.drawable.ic_groceries
            "ic_transport"      -> R.drawable.ic_transport
            "ic_shopping"       -> R.drawable.ic_shopping
            "ic_social"         -> R.drawable.ic_social
            "ic_culture"        -> R.drawable.ic_culture
            "ic_sports"         -> R.drawable.ic_sports
            "ic_healthcare"     -> R.drawable.ic_healthcare
            "ic_travel"         -> R.drawable.ic_travel
            "ic_education"      -> R.drawable.ic_education
            "ic_subscriptions"  -> R.drawable.ic_subscriptions
            "ic_pets"           -> R.drawable.ic_pets
            "ic_family_friends" -> R.drawable.ic_family_friends
            else                -> -1
        }
    }

    @StringRes
    fun getStringResource(value: String): Int {
        return when (value) {
            "category_job"            -> R.string.category_job
            "category_house"          -> R.string.category_house
            "category_groceries"      -> R.string.category_groceries
            "category_transport"      -> R.string.category_transport
            "category_shopping"       -> R.string.category_shopping
            "category_social"         -> R.string.category_social
            "category_culture"        -> R.string.category_culture
            "category_sports"         -> R.string.category_sports
            "category_healthcare"     -> R.string.category_healthcare
            "category_travel"         -> R.string.category_travel
            "category_education"      -> R.string.category_education
            "category_subscriptions"  -> R.string.category_subscriptions
            "category_pets"           -> R.string.category_pets
            "category_family_friends" -> R.string.category_family_friends
            else                      -> -1
        }
    }

    fun toStringValue(@StringRes res: Int): String {
        return when (res) {
            R.string.category_job            -> "category_job"
            R.string.category_house          -> "category_house"
            R.string.category_groceries      -> "category_groceries"
            R.string.category_transport      -> "category_transport"
            R.string.category_shopping       -> "category_shopping"
            R.string.category_social         -> "category_social"
            R.string.category_culture        -> "category_culture"
            R.string.category_sports         -> "category_sports"
            R.string.category_healthcare     -> "category_healthcare"
            R.string.category_travel         -> "category_travel"
            R.string.category_education      -> "category_education"
            R.string.category_subscriptions  -> "category_subscriptions"
            R.string.category_pets           -> "category_pets"
            R.string.category_family_friends -> "category_family_friends"
            else                             -> ""
        }
    }

    fun toDrawableValue(@DrawableRes res: Int): String {
        return when (res) {
            R.drawable.ic_franc          -> "ic_franc"
            R.drawable.ic_euro           -> "ic_euro"
            R.drawable.ic_dollar         -> "ic_dollar"
            R.drawable.ic_pound          -> "ic_pound"
            R.drawable.ic_job            -> "ic_job"
            R.drawable.ic_house          -> "ic_house"
            R.drawable.ic_groceries      -> "ic_groceries"
            R.drawable.ic_transport      -> "ic_transport"
            R.drawable.ic_shopping       -> "ic_shopping"
            R.drawable.ic_social         -> "ic_social"
            R.drawable.ic_culture        -> "ic_culture"
            R.drawable.ic_sports         -> "ic_sports"
            R.drawable.ic_healthcare     -> "ic_healthcare"
            R.drawable.ic_travel         -> "ic_travel"
            R.drawable.ic_education      -> "ic_education"
            R.drawable.ic_subscriptions  -> "ic_subscriptions"
            R.drawable.ic_pets           -> "ic_pets"
            R.drawable.ic_family_friends -> "ic_family_friends"
            else                         -> ""
        }
    }
}