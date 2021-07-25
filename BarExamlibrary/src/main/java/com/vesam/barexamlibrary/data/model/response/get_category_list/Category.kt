package com.vesam.barexamlibrary.data.model.response.get_category_list


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("jalali_updated_at")
    val jalaliUpdatedAt: String,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("slide_image")
    val slideImage: String,
    @SerializedName("sort")
    val sort: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)