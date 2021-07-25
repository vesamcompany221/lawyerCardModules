package com.vesam.barexamlibrary.data.api

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun initGetCategoryList(userUuid: String, userApiToken: String) =
        try {
            apiService.initGetCategoryList(userUuid, userApiToken)
        } catch (e: Exception) {
            e
        }

    override suspend fun initGetCategoryList(
        userUuid: String,
        userApiToken: String,
        categoryId: Int
    ) =
        try {
            apiService.initGetCategoryList(userUuid, userApiToken, categoryId)
        } catch (e: Exception) {
            e
        }

    override suspend fun initGetCategoryDetail(
        userUuid: String,
        userApiToken: String,
        quizId: Int
    ) =
        try {
            apiService.initGetCategoryDetail(userUuid, userApiToken, quizId)
        } catch (e: Exception) {
            e
        }

    override suspend fun initGetQuizQuestion(
        userUuid: String,
        userApiToken: String,
        quizId: Int,
        questionId: Int
    ) =
        try {
            apiService.initGetQuizQuestion(userUuid, userApiToken, quizId, questionId)
        } catch (e: Exception) {
            e
        }

    override suspend fun initGetQuizQuestionResult(
        userUuid: String,
        userApiToken: String,
        quizId: Int
    )=
        try {
            apiService.initGetQuizQuestionResult(userUuid, userApiToken, quizId)
        } catch (e: Exception) {
            e
        }

    override suspend fun initSetQuizQuestion(
        userUuid: String,
        userApiToken: String,
        quizId: Int,
        userAnswers: String
    ) =
        try {
            apiService.initSetQuizQuestion(userUuid, userApiToken, quizId, userAnswers)
        } catch (e: Exception) {
            e
        }

    override suspend fun initOnlinePayment(
        userUuid: String,
        userApiToken: String,
        quizId: Int
    ) =
        try {
            apiService.initOnlinePayment(userUuid, userApiToken, quizId)
        } catch (e: Exception) {
            e
        }

    override suspend fun initBuyWallet(userUuid: String, userApiToken: String, quizId: Int) =
        try {
            apiService.initBuyWallet(userUuid, userApiToken, quizId)
        } catch (e: Exception) {
            e
        }


}