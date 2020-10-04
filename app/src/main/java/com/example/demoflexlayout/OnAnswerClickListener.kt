package com.example.demoflexlayout

import android.view.View

interface OnAnswerListener {
    fun onChoose(v: View?, question: Question, answer: Answer)
    fun onInput(v: View?, question: Question, textInput: String)
}