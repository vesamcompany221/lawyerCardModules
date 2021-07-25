package com.vesam.barexamlibrary.utils.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.StrictMode
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.google.android.material.imageview.ShapeableImageView
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.utils.zoomage.ZoomageView
import java.io.InputStream
import java.net.URL

class GlideTools(private val context: Context, private val handelErrorTools: HandelErrorTools) {

    fun displayImageOriginal(img: ImageView?, url: String?) {
        try {
            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_load_pic)
            img?.let {
                Glide.with(context).load(url)
                    .apply(options)
                    .into(it)
            }
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    fun displayHome(img: ImageView, url: String) {
        try {
            val options: RequestOptions = RequestOptions()
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_photo)
            Glide.with(context).load(url)
                .apply(options)
                .into(img)
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    fun displayImageSliderZoom(imageViewZoomable: SubsamplingScaleImageView, url: String?) {
        try {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        imageViewZoomable.setImage(ImageSource.bitmap(resource))
                    }
                })
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    fun displayImageSliderZoom(imageViewZoomable: ZoomageView, url: String?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val urls = URL(url)
            imageViewZoomable.setImageBitmap(BitmapFactory.decodeStream(urls.content as InputStream))
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }


    fun displayImageSliderDefault(img: ImageView, url: String) {
        try {
            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_no_pic_detail)
                .error(R.drawable.ic_error)
            Glide.with(context).load(url)
                .apply(options)
                .into(img)
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    fun displaySliderImage(img: ShapeableImageView, url: String, placeholder: Int, error: Int) {
        try {
            img.tag=url
            val options: RequestOptions = RequestOptions()
                .placeholder(placeholder)
                .error(error)
            img.let {
                Glide.with(context).load(url)
                    .apply(options)
                    .into(it)
            }
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }
}