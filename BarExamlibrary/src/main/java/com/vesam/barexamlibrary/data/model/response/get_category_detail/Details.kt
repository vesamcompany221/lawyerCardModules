package com.vesam.barexamlibrary.data.model.response.get_category_detail


import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("category")
    val category: Category,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val isActive: String,
    @SerializedName("jalali_created_at")
    val jalaliCreatedAt: String,
    @SerializedName("pass_condition")
    val passCondition: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("question_count")
    val questionCount: Int,
    @SerializedName("slide_image")
    val slideImage: String,
    @SerializedName("sort")
    val sort: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("user_already_taken_quiz")
    val userAlreadyTakenQuiz: Boolean
)