package com.vesam.barexamlibrary.data.model.response.set_quiz_question


import com.google.gson.annotations.SerializedName

data class ResponseSetQuizQuestionModel(
    @SerializedName("quiz")
    val quiz: Quiz
)