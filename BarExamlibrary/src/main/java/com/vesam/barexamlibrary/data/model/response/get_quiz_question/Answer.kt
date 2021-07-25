package com.vesam.barexamlibrary.data.model.response.get_quiz_question


import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_correct")
    val isCorrect: Boolean,
    @SerializedName("sort")
    val sort: Int,
    @SerializedName("title")
    val title: String
) {
    var activation = false
}