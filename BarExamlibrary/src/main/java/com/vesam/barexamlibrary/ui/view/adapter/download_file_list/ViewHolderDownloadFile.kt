package com.vesam.barexamlibrary.ui.view.adapter.download_file_list

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.vesam.barexamlibrary.R

class ViewHolderDownloadFile(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
    val image = itemView.findViewById(R.id.image) as ShapeableImageView
    val lnParent = itemView.findViewById(R.id.lnParent) as LinearLayout
}