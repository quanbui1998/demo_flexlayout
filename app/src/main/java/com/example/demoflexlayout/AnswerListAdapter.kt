package com.example.demoflexlayout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.answer_item.view.*

class AnswerListAdapter(
    private val question: Question,
    private val onAnswerListener: OnAnswerListener,
    private val viewModel: MainActivity
) :
    RecyclerView.Adapter<AnswerListAdapter.ViewHolder>() {
    private var listAnswer : ArrayList<Answer> = question.listAnswer

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btnAnswer: TextView = itemView.btn_answer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.answer_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAnswer.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = listAnswer[position]
        holder.btnAnswer.text = answer.content
        if (viewModel.isAnswerChoose(question, answer)) {
            holder.btnAnswer.setBackgroundResource(R.drawable.answer_shape_choose)
        } else {
            holder.btnAnswer.setBackgroundResource(R.drawable.answer_shape)
        }
        holder.btnAnswer.setOnClickListener { v: View? ->
            run {
                onAnswerListener.onChoose(v, question, answer)
                if (question.type == TYPE_CHOOSE_MULTI) {
                    notifyItemChanged(position)
                } else {
                    notifyDataSetChanged()
                }
            }
        }
    }
}