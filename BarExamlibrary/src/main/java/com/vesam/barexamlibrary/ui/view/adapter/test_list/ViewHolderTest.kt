package com.vesam.barexamlibrary.ui.view.adapter.test_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.vesam.barexamlibrary.R

class ViewHolderTest(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById(R.id.image) as ShapeableImageView
    val txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
    val txtDescription = itemView.findViewById(R.id.txtDescription) as TextView
    val txtQuestionCount = itemView.findViewById(R.id.txtQuestionCount) as TextView
    val txtMoney = itemView.findViewById(R.id.txtMoney) as TextView
    val lnParent = itemView.findViewById(R.id.lnParent) as View
}