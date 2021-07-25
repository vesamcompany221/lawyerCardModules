@file:Suppress("DEPRECATION")

package com.vesam.barexamlibrary.utils.extention

import android.content.Context
import android.content.res.ColorStateList
import com.google.android.material.radiobutton.MaterialRadioButton


fun setCircleColor(context: Context,radioButton: MaterialRadioButton, color: Int){
    val myColorStateList = ColorStateList(
        arrayOf(intArrayOf(context.resources.getColor(color))),
        intArrayOf(context.resources.getColor(color))
    )

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) radioButton.buttonTintList = myColorStateList
}