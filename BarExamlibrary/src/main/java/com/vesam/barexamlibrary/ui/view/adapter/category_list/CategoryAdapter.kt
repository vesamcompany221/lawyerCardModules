package com.vesam.barexamlibrary.ui.view.adapter.category_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.get_category_list.Category
import com.vesam.barexamlibrary.data.model.response.get_category_list.Quizze
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import com.vesam.barexamlibrary.utils.tools.GlideTools
import java.util.*

class CategoryAdapter(private val context: Context,private val glideTools: GlideTools) : RecyclerView.Adapter<ViewHolderCategory>() {

    private val list: ArrayList<Category> = ArrayList()
    private lateinit var onClickListenerAny: OnClickListenerAny

    fun setOnItemClickListener(onClickListenerAny: OnClickListenerAny) {
        this.onClickListenerAny = onClickListenerAny
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategory =
        ViewHolderCategory(getViewHolder(parent))

    private fun getViewHolder(parent: ViewGroup): View = LayoutInflater.from(context)
        .inflate(R.layout.item_categorys, parent, false)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderCategory, position: Int) {
        val quiz = list[position]
        holder.txtTitle.text = quiz.title
        glideTools.displaySliderImage(
            holder.image,
            quiz.slideImage,
            R.drawable.shape_round_slider_place_holder,
            R.drawable.shape_round_slider_place_holder
        )
        holder.lnParent.setOnClickListener { onClickListenerAny.onClickListener(quiz) }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(listCategory: List<Category>) {
        list.clear()
        list.addAll(listCategory)
        notifyDataSetChanged()
    }
}