package com.vesam.barexamlibrary.ui.view.adapter.answer_list

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.vesam.barexamlibrary.R

class ViewHolderAnswer(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
    val imgCircle = itemView.findViewById(R.id.imgCircle) as ShapeableImageView
    val lnParent = itemView.findViewById(R.id.lnParent) as View
}