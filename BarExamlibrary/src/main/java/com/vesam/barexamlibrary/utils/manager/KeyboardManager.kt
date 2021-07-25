package com.vesam.barexamlibrary.utils.manager

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import com.vesam.barexamlibrary.utils.application.AppQuiz.Companion.activity


class KeyboardManager {

    fun hideKeyboard() {
        val v = activity.currentFocus
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (v != null)
            imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun showKeyboard() {
        val v = activity.currentFocus
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (v != null)
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
    }

}