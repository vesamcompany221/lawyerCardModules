package com.vesam.barexamlibrary.data.repository.quiz_repository

import com.vesam.barexamlibrary.data.api.ApiHelper


class QuizRepository(private val apiHelper: ApiHelper) {

    suspend fun initGetCategoryList(userUuid: String,userApiToken: String) = apiHelper.initGetCategoryList(userUuid, userApiToken)
    suspend fun initGetCategoryList(userUuid: String,userApiToken: String, categoryId: Int) = apiHelper.initGetCategoryList(userUuid, userApiToken,categoryId)
    suspend fun initGetCategoryDetail(userUuid: String,userApiToken: String, quizId: Int) = apiHelper.initGetCategoryDetail(userUuid, userApiToken,quizId)
    suspend fun initGetQuizQuestion(userUuid: String,userApiToken: String, quizId: Int,questionId: Int) = apiHelper.initGetQuizQuestion(userUuid, userApiToken,quizId,questionId)
    suspend fun initSetQuizQuestion(userUuid: String,userApiToken: String, quizId: Int,userAnswers: String) = apiHelper.initSetQuizQuestion(userUuid, userApiToken,quizId,userAnswers)
    suspend fun initGetQuizQuestionResult(userUuid: String,userApiToken: String, quizId: Int) = apiHelper.initGetQuizQuestionResult(userUuid, userApiToken,quizId)
    suspend fun initOnlinePayment(userUuid: String,userApiToken: String, quizId: Int) = apiHelper.initOnlinePayment(userUuid, userApiToken,quizId)
    suspend fun initBuyWallet(userUuid: String,userApiToken: String, quizId: Int) = apiHelper.initBuyWallet(userUuid, userApiToken,quizId)
}