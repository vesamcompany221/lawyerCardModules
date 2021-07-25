package com.vesam.barexamlibrary.data.api

interface ApiHelper {

    // quiz -----------------------------------
    suspend fun initGetCategoryList(userUuid: String, userApiToken: String): Any
    suspend fun initGetCategoryList(userUuid: String, userApiToken: String, categoryId: Int): Any
    suspend fun initGetCategoryDetail(userUuid: String, userApiToken: String, quizId: Int): Any
    suspend fun initGetQuizQuestion(userUuid: String, userApiToken: String, quizId: Int,questionId: Int): Any
    suspend fun initSetQuizQuestion(userUuid: String, userApiToken: String, quizId: Int,userAnswers: String): Any
    suspend fun initGetQuizQuestionResult(userUuid: String, userApiToken: String, quizId: Int): Any
    suspend fun initOnlinePayment(userUuid: String, userApiToken: String, quizId: Int): Any
    suspend fun initBuyWallet(userUuid: String, userApiToken: String, quizId: Int): Any

}