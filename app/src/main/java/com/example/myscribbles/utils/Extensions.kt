package com.example.myscribbles.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File


fun getProgressDrawable(context: Context): CircularProgressDrawable? {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 15f
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable?) {
    println("image at $url")
    val options = RequestOptions.placeholderOf(progressDrawable)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}