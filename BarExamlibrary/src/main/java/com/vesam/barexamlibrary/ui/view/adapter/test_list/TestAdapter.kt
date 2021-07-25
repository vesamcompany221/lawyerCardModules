package com.vesam.barexamlibrary.ui.view.adapter.test_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.get_category_list.Quizze
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import com.vesam.barexamlibrary.utils.tools.GlideTools
import java.util.*

class TestAdapter(private val context: Context, private val glideTools: GlideTools) :
    RecyclerView.Adapter<ViewHolderTest>() {

    private val list: ArrayList<Quizze> = ArrayList()
    private lateinit var onClickListenerAny: OnClickListenerAny

    fun setOnItemClickListener(onClickListenerAny: OnClickListenerAny) {
        this.onClickListenerAny = onClickListenerAny
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTest =
        ViewHolderTest(getViewHolder(parent))

    private fun getViewHolder(parent: ViewGroup): View = LayoutInflater.from(context)
        .inflate(R.layout.item_test, parent, false)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderTest, position: Int) {
        val test = list[position]
        holder.txtTitle.text = test.title
        holder.txtDescription.text = test.categoryName
        holder.txtQuestionCount.text =
            "${test.questionCount} ${context.resources.getString(R.string.questions)}"
        holder.txtMoney.text = initPrice(test.price)
        glideTools.displaySliderImage(
            holder.image,
            test.slideImage,
            R.drawable.shape_round_slider_place_holder,
            R.drawable.shape_round_slider_place_holder
        )
        holder.lnParent.setOnClickListener { onClickListenerAny.onClickListener(test) }
    }

    private fun initPrice(price: Int): String = when {
        price > 0 -> "$price ${context.resources.getString(R.string.unit_money)}"
        price == 0 -> context.resources.getString(R.string.free)
        else -> context.resources.getString(R.string.paid)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(listQuizze: List<Quizze>) {
        list.clear()
        list.addAll(listQuizze)
        notifyDataSetChanged()
    }
}