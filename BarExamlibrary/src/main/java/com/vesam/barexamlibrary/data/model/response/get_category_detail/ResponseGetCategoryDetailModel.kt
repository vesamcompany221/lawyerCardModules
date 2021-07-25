package com.vesam.barexamlibrary.data.model.response.get_category_detail


import com.google.gson.annotations.SerializedName

data class ResponseGetCategoryDetailModel(
    @SerializedName("details")
    val details: Details
)