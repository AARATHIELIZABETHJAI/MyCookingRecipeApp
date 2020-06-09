package com.example.tastebuds.util

import android.R.attr
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.net.URL


@BindingAdapter("app:image")
fun setImage(view: ImageView, url: String?) {
    if (url != null) {
        val base = URL("https://spoonacular.com/recipeImages/")
        val mergedURL = URL(base, url)
        Glide.with(view).load(mergedURL).apply(
            RequestOptions().placeholder(R.drawable.ic_room_service_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp).centerCrop()
        ).into(view)
    }
}


@BindingAdapter("app:recipeimage")
fun setrecipeImage(view: ImageView, url: String?) {
    if (url != null) {
        Glide.with(view).load(url).apply(
            RequestOptions().placeholder(R.drawable.ic_room_service_black_24dp)
                .error(R.drawable.ic_room_service_black_24dp).centerCrop()
        ).into(view)
    }
}

@BindingAdapter("app:imageUrl")
fun setimageUrl(layout: LinearLayout, url: String?) {
    if (url != null) {

        Glide.with(layout.context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val drawable: Drawable = BitmapDrawable(layout.context.getResources()!!, resource)
                    layout.setBackground(drawable)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
//        Glide.with(view).load(url).apply(
//            RequestOptions().placeholder(R.drawable.ic_room_service_black_24dp)
//                .error(R.drawable.ic_room_service_black_24dp).centerCrop()
//        ).into(view)
    }
}