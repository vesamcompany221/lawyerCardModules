package com.vesam.barexamlibrary.ui.view.adapter.answer_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.get_quiz_question.Answer
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import java.util.*

class AnswerAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolderAnswer>() {

    private val list: ArrayList<Answer> = ArrayList()
    private lateinit var onClickListenerAny: OnClickListenerAny

    fun setOnItemClickListener(onClickListenerAny: OnClickListenerAny) {
        this.onClickListenerAny = onClickListenerAny
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAnswer =
        ViewHolderAnswer(getViewHolder(parent))

    private fun getViewHolder(parent: ViewGroup): View = LayoutInflater.from(context)
        .inflate(R.layout.item_answer, parent, false)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderAnswer, position: Int) {
        val answer = list[position]
        holder.txtTitle.text = answer.title.trim()
        initCheckActivation(holder, answer)
        holder.lnParent.setOnClickListener { onClickListenerAny.onClickListener(answer) }
    }

    private fun initCheckActivation(holder: ViewHolderAnswer, answer: Answer) =
        when {
            answer.activation -> {
                holder.lnParent.setBackgroundResource(R.drawable.shape_round_answer_active)
                holder.imgCircle.setImageResource(R.drawable.ic_active_circle)
            }
            else -> {
                holder.lnParent.setBackgroundResource(R.drawable.shape_round_answer_in_active)
                holder.imgCircle.setImageResource(R.drawable.ic_in_active_circle)
            }
        }

    override fun getItemCount(): Int = list.size

    fun updateList(listAnswer: List<Answer>) {
        list.clear()
        list.addAll(listAnswer)
        notifyDataSetChanged()
    }

    fun updateItemList(answer: Answer) {
        initResetList()
        val index = list.indexOf(answer)
        answer.activation = true
        list[index] = answer
        notifyDataSetChanged()
    }

    private fun initResetList() = list.forEach { it.activation = false }
}