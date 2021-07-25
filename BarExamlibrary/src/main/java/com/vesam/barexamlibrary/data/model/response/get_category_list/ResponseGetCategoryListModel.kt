package com.vesam.barexamlibrary.data.model.response.get_category_list


import com.google.gson.annotations.SerializedName

data class ResponseGetCategoryListModel(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("quizzes")
    val quizzes: List<Quizze>
)