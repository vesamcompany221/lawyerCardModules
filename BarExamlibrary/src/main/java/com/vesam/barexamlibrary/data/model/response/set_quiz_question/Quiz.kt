package com.vesam.barexamlibrary.data.model.response.set_quiz_question


import com.google.gson.annotations.SerializedName

data class Quiz(
    @SerializedName("gift_charge_wallet")
    val giftChargeWallet: Int,
    @SerializedName("gift_description")
    val giftDescription: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("question_count")
    val questionCount: Int,
    @SerializedName("result")
    val result: Result,
    @SerializedName("title")
    val title: String
)