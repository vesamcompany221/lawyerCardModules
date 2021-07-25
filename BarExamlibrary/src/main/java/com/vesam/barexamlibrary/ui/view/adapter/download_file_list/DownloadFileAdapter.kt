package com.vesam.barexamlibrary.ui.view.adapter.download_file_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.local.file_download.FileDownload
import com.vesam.barexamlibrary.data.model.response.get_category_list.Category
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import com.vesam.barexamlibrary.utils.tools.GlideTools
import java.util.*

class DownloadFileAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolderDownloadFile>() {

    private val list: ArrayList<FileDownload> = ArrayList()
    private lateinit var onClickListenerAny: OnClickListenerAny

    fun setOnItemClickListener(onClickListenerAny: OnClickListenerAny) {
        this.onClickListenerAny = onClickListenerAny
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDownloadFile =
        ViewHolderDownloadFile(getViewHolder(parent))

    private fun getViewHolder(parent: ViewGroup): View = LayoutInflater.from(context)
        .inflate(R.layout.item_download_file, parent, false)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderDownloadFile, position: Int) {
        val quiz = list[position]
        holder.txtTitle.text = quiz.title
        holder.lnParent.setOnClickListener { onClickListenerAny.onClickListener(quiz) }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(listFileDownload: List<FileDownload>) {
        list.clear()
        list.addAll(listFileDownload)
        notifyDataSetChanged()
    }
}