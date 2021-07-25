package com.vesam.barexamlibrary.data.model.response.online_payment


import com.google.gson.annotations.SerializedName

data class ResponseOnlinePaymentModel(
    @SerializedName("url")
    val url: String
)