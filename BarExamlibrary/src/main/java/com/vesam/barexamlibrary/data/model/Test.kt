package com.vesam.barexamlibrary.data.model


import com.google.gson.annotations.SerializedName

data class Test(
    @SerializedName("description")
    val description: String,
    @SerializedName("imageId")
    var imageId: Int,
    @SerializedName("money")
    val money: String,
    @SerializedName("questionCount")
    val questionCount: String,
    @SerializedName("title")
    val title: String
)