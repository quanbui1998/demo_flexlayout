package com.example.demoflexlayout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.question_item.view.*


class QuestionListAdapter(
    private val context: Context,
    private val onAnswerListener: OnAnswerListener,
    private val viewModel: MainActivity
) :
    RecyclerView.Adapter<QuestionListAdapter.ViewHolder>() {

    private var listQuestion = ArrayList<Question>()

    fun setListQuestion(list: ArrayList<Question>) {
        listQuestion = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvContent: TextView = itemView.tv_content
        var rvListAnswer: RecyclerView = itemView.rv_list_answer
        var edtAnswerInput: EditText = itemView.edt_answer_input
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listQuestion.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = listQuestion.get(position)
        holder.tvContent.text = question.content

        if (question.type == TYPE_CHOOSE_ONE || question.type == TYPE_CHOOSE_MULTI) {
            holder.rvListAnswer.visibility = View.VISIBLE
            holder.edtAnswerInput.visibility = View.GONE
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.flexWrap = FlexWrap.WRAP
            holder.rvListAnswer.layoutManager = layoutManager
            holder.rvListAnswer.adapter = AnswerListAdapter(
                question,
                onAnswerListener,
                viewModel
            )
        } else {
            holder.rvListAnswer.visibility = View.GONE
            holder.edtAnswerInput.visibility = View.VISIBLE
            holder.edtAnswerInput.setOnFocusChangeListener { v, hasFocus ->
                run {
                    if (!hasFocus) {
                        onAnswerListener.onInput(v, question, holder.edtAnswerInput.text.toString())
                    }
                }
            }
        }
    }
}