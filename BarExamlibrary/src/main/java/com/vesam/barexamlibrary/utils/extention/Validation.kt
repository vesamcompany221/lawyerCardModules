package com.vesam.barexamlibrary.utils.extention

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import java.util.regex.Matcher
import java.util.regex.Pattern

@SuppressLint("RtlHardcoded")
fun checkPersianCharacter(persianCharacter: String, textView: TextView) {
    val rtlCharacter: Pattern =
        Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]")
    val matcher: Matcher = rtlCharacter.matcher(persianCharacter)
    when {
        matcher.find() -> textView.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
        else -> textView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
    }
}

@SuppressLint("RtlHardcoded")
fun checkPersianCharacter(persianCharacter: String, view: LinearLayout) {
    val rtlCharacter: Pattern =
        Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]")
    val matcher: Matcher = rtlCharacter.matcher(persianCharacter)
    when {
        matcher.find() -> view.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
        else -> view.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
    }
}

@SuppressLint("RtlHardcoded")
fun checkPersianCharacter(persianCharacter: String, view: LinearLayoutCompat) {
    val rtlCharacter: Pattern =
        Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]")
    val matcher: Matcher = rtlCharacter.matcher(persianCharacter)
    when {
        matcher.find() -> view.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
        else -> view.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
    }
}

@SuppressLint("RtlHardcoded")
fun checkPersianCharacter(
    persianCharacter: String,
    viewStart: LinearLayout,
    viewEnd: LinearLayout
) {
    val rtlCharacter: Pattern =
        Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]")
    val matcher: Matcher = rtlCharacter.matcher(persianCharacter)
    when {
        matcher.find() -> {
            viewStart.visibility = View.GONE
            viewEnd.visibility = View.VISIBLE
        }
        else -> {
            viewStart.visibility = View.VISIBLE
            viewEnd.visibility = View.GONE
        }
    }
}