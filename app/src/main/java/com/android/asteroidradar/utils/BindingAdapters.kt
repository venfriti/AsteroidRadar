package com.android.asteroidradar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import coil.load
import com.squareup.picasso.Picasso
import com.android.asteroidradar.R
import com.android.asteroidradar.main.PictureState
import com.android.asteroidradar.models.Asteroid
import com.android.asteroidradar.models.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, pictureOfDay: LiveData<PictureState>){
    imageView.load(pictureOfDay.value?.pictureUrl?.url)
}

@BindingAdapter("codename")
fun TextView.codename(item: Asteroid?){
    item?.let {
        text = item.codename
    }
}

@BindingAdapter("closeApproachDate")
fun TextView.closeApproachDate(item: Asteroid?){
    item?.let {
        text = item.closeApproachDate
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

