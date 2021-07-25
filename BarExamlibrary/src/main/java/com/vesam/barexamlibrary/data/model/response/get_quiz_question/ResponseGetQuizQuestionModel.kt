package com.vesam.barexamlibrary.data.model.response.get_quiz_question


import com.google.gson.annotations.SerializedName

data class ResponseGetQuizQuestionModel(
    @SerializedName("question")
    val question: Question
)