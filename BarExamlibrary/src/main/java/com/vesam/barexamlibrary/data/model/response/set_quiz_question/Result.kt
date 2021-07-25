package com.vesam.barexamlibrary.data.model.response.set_quiz_question


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("correct_answers_count")
    val correctAnswersCount: Int,
    @SerializedName("incorrect_answers_count")
    val incorrectAnswersCount: Int,
    @SerializedName("passed")
    val passed: Boolean,
    @SerializedName("percent_to_passed")
    val percentToPassed: Int,
    @SerializedName("user_percent")
    val userPercent: Int
)