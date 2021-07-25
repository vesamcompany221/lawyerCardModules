package com.vesam.barexamlibrary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vesam.barexamlibrary.data.repository.quiz_repository.QuizRepository
import com.vesam.barexamlibrary.utils.abstracts.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel(
    private val quizRepository: QuizRepository
) : BaseViewModel() {

    fun initGetCategoryList(userUuid: String, userApiToken: String): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initGetCategoryList(userUuid, userApiToken).let(liveData::postValue)
            }
        }
        return liveData
    }

    fun initGetCategoryList(userUuid: String, userApiToken: String, categoryId: Int): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initGetCategoryList(userUuid, userApiToken,categoryId).let(liveData::postValue)
            }
        }
        return liveData
    }

    fun initGetCategoryDetail(userUuid: String, userApiToken: String, quizId: Int): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initGetCategoryDetail(userUuid, userApiToken,quizId).let(liveData::postValue)
            }
        }
        return liveData
    }

    fun initGetQuizQuestion(userUuid: String, userApiToken: String, quizId: Int,questionId: Int): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initGetQuizQuestion(userUuid, userApiToken,quizId,questionId).let(liveData::postValue)
            }
        }
        return liveData
    }

    fun initSetQuizQuestion(userUuid: String, userApiToken: String, quizId: Int,userAnswers: String): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initSetQuizQuestion(userUuid, userApiToken,quizId,userAnswers).let(liveData::postValue)
            }
        }
        return liveData
    }

    fun initGetQuizQuestionResult(userUuid: String, userApiToken: String, quizId: Int): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initGetQuizQuestionResult(userUuid, userApiToken,quizId).let(liveData::postValue)
            }
        }
        return liveData
    }

    fun initOnlinePayment(userUuid: String, userApiToken: String, quizId: Int): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initOnlinePayment(userUuid, userApiToken,quizId).let(liveData::postValue)
            }
        }
        return liveData
    }

    fun initBuyWallet(userUuid: String, userApiToken: String, quizId: Int): LiveData<Any> {
        val liveData = MutableLiveData<Any>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quizRepository.initBuyWallet(userUuid, userApiToken,quizId).let(liveData::postValue)
            }
        }
        return liveData
    }


}