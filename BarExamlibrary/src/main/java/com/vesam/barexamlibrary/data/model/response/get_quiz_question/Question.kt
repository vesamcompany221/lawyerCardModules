package com.vesam.barexamlibrary.data.model.response.get_quiz_question


import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("answers")
    val answers: List<Answer>,
    @SerializedName("description")
    val description: Description,
    @SerializedName("id")
    val id: Int,
    @SerializedName("next_question_id")
    val nextQuestionId: Int,
    @SerializedName("preview_question_id")
    val previewQuestionId: Int,
    @SerializedName("sort")
    val sort: Int,
    @SerializedName("title")
    val title: String
)