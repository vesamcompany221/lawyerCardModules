package com.vesam.barexamlibrary.utils.manager

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.gson.Gson
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.interfaces.OnClickListener
import com.vesam.barexamlibrary.utils.application.AppQuiz.Companion.activity
import com.vesam.barexamlibrary.utils.tools.ToastTools

class DialogManager(
    private val gson: Gson,
    private val toastTools: ToastTools,
) {
    fun initExitApp(description: String, onClickListener: OnClickListener) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_exit)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val txtDescription = dialog.findViewById<TextView>(R.id.txtDescription)
        val txtNo = dialog.findViewById<View>(R.id.txtNo)
        val txtYes = dialog.findViewById<View>(R.id.txtYes)
        txtDescription.text=description
        txtNo.setOnClickListener { dialog.dismiss() }
        txtYes.setOnClickListener {
            dialog.dismiss()
            onClickListener.onClickListener()
        }
        dialog.show()
    }

}