package com.example.demoflexlayout

import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnAnswerListener {
    private lateinit var rvListQuestion: RecyclerView
    private lateinit var btnSubmit: Button
    private lateinit var nsvSurvey : NestedScrollView
    private lateinit var adapter: QuestionListAdapter

    private val listQuestion = ArrayList<Question>()
    private val answerOfChooseQuestion = HashMap<Int, ArrayList<Int>>()
    private val answerOfInputQuestion = HashMap<Int, String>()

    init {
        listQuestion.add(
            Question(
                1,
                "Question 1",
                arrayListOf(
                    Answer(1, "Who are you and manan"),
                    Answer(2, "Answer 1"),
                    Answer(3, "Answer 2"),
                    Answer(4, "Answer 3"),
                    Answer(5, "Answer 4"),
                    Answer(6, "Answer 5")
                ),
                true,
                TYPE_CHOOSE_MULTI
            )
        )
        listQuestion.add(
            Question(
                2, "Question 2", arrayListOf(Answer(1, "Answer 1"), Answer(2, "Answer 2")), true,
                TYPE_CHOOSE_ONE
            )
        )
        listQuestion.add(
            Question(
                3, "Question 3", ArrayList(), true,
                TYPE_INPUT
            )
        )
        listQuestion.add(
            Question(
                4, "Question 4", ArrayList(), true,
                TYPE_INPUT
            )
        )
        listQuestion.add(
            Question(
                5,
                "Question 5",
                arrayListOf(
                    Answer(1, "Who are you and manan"),
                    Answer(2, "Answer 1"),
                    Answer(3, "Answer 2"),
                    Answer(4, "Answer 3"),
                    Answer(5, "Answer 4"),
                    Answer(6, "Answer 5")
                ),
                true,
                TYPE_CHOOSE_MULTI
            )
        )
        listQuestion.add(
            Question(
                6,
                "Question 6",
                arrayListOf(
                    Answer(1, "Who are you and manan"),
                    Answer(2, "Answer 1"),
                    Answer(3, "Answer 2"),
                    Answer(4, "Answer 3"),
                    Answer(5, "Answer 4"),
                    Answer(6, "Answer 5")
                ),
                true,
                TYPE_CHOOSE_MULTI
            )
        )
        listQuestion.add(
            Question(
                7,
                "Question 7",
                arrayListOf(
                    Answer(1, "Who are you and manan"),
                    Answer(2, "Answer 1"),
                    Answer(3, "Answer 2"),
                    Answer(4, "Answer 3"),
                    Answer(5, "Answer 4"),
                    Answer(6, "Answer 5")
                ),
                true,
                TYPE_CHOOSE_MULTI
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvListQuestion = rv_list_question
        btnSubmit = btn_submit
        nsvSurvey = nsv_survey
        adapter = QuestionListAdapter(applicationContext, this, this)
        rvListQuestion.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        rvListQuestion.layoutManager = layoutManager
        adapter.setListQuestion(listQuestion)
        btnSubmit.setOnClickListener { v: View? -> handleSubmit() }
    }

    override fun onChoose(v: View?, question: Question, answer: Answer) {
        if (answerOfChooseQuestion.containsKey(question.id)) {
            val list = answerOfChooseQuestion[question.id]!!
            if (list.contains(answer.id)) {
                list.remove(answer.id)
            } else {
                if (question.type == TYPE_CHOOSE_ONE) {
                    list.clear()
                }
                list.add(answer.id)
            }
        } else {
            answerOfChooseQuestion[question.id] = arrayListOf(answer.id)
        }
    }

    override fun onInput(v: View?, question: Question, textInput: String) {
        answerOfInputQuestion[question.id] = textInput
    }

    fun isAnswerChoose(question: Question, answer: Answer): Boolean {
        return answerOfChooseQuestion.containsKey(question.id) && answerOfChooseQuestion[question.id]?.contains(
            answer.id
        )!!
    }

    private fun handleSubmit() {
        if (isAnswerAllRequiredQuestions()) {
            //todo
        }
    }

    private fun isAnswerAllRequiredQuestions(): Boolean {
        for ((index, question) in listQuestion.withIndex()) {
            if (question.isRequired) {
                var check = true
                if (question.type == TYPE_CHOOSE_ONE || question.type == TYPE_CHOOSE_MULTI) {
                    if (!answerOfChooseQuestion.containsKey(question.id) || answerOfChooseQuestion[question.id]?.isEmpty()!!) {
                        check = false
                    }
                } else {
                    if (!answerOfInputQuestion.containsKey(question.id) || answerOfInputQuestion[question.id]?.isBlank()!!) {
                        check = false
                    }
                }
                if (!check) {
                    Handler().postDelayed({
                        val y = rvListQuestion.getChildAt(index).y
                        nsvSurvey.fling(0)
                        nsvSurvey.smoothScrollTo(0, y.toInt())
                    }, 100)
                    showRemindAnswerRequiredQuestion(index)
                    return false;
                }
            }
        }
        return true
    }

    private fun showRemindAnswerRequiredQuestion(questionIndex: Int) {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage(
                "Please answer question number " + (questionIndex + 1)
            )
            .setPositiveButton("OK", null)
            .show()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}