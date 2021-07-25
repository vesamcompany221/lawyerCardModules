package com.vesam.barexamlibrary.data.model.response.get_quiz_question


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("content")
    val content: String,
    @SerializedName("format")
    val format: String
)