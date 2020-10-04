package com.example.demoflexlayout


const val TYPE_CHOOSE_ONE = 1;
const val TYPE_CHOOSE_MULTI = 2;
const val TYPE_INPUT = 3;

class Question(
    var id: Int,
    var content: String,
    var listAnswer: ArrayList<Answer>,
    var isRequired: Boolean,
    var type: Int
) {
}