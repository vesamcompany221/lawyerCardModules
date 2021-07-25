package com.vesam.barexamlibrary.data.model.response.get_category_list


import com.google.gson.annotations.SerializedName

data class Quizze(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jalali_created_at")
    val jalaliCreatedAt: String,
    @SerializedName("pass_condition")
    val passCondition: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("question_count")
    val questionCount: Int,
    @SerializedName("sort")
    val sort: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("slide_image")
    val slideImage: String,
    @SerializedName("category_name")
    val categoryName: String
)