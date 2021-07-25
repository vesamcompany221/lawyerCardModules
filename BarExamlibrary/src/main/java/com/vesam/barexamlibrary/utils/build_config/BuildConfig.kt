package com.vesam.barexamlibrary.utils.build_config


class BuildConfig {
    companion object {
        var BASE_URL = ""

        // header
        const val CONTENT_TYPE_HEADER = "Content-Type"
        const val APPLICATION_JSON_HEADER = "application/json"
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "

        // header
        const val USER_UUID = "user_uuid"
        const val USER_API_TOKEN = "user_api_token"
        const val UUID = "uuid"
        const val API_TOKEN = "api_token"

        // header value
        var USER_UUID_VALUE = ""
        var USER_API_TOKEN_VALUE = ""

        // api
        const val GET_CATEGORY_LIST = "quiz/get-category-list"
        const val GET_CATEGORY_DETAIL = "quiz/get-quiz-with-details"
        const val GET_QUIZ_QUESTION = "quiz/get-quiz-question"
        const val SET_QUIZ_QUESTION = "quiz/set-quiz-result"
        const val GET_QUIZ_RESULT = "quiz/get-quiz-result"
        const val ONLINE_PAYMENT = "client/payment/create"
        const val BUY_WALLET = "client/wallet/buy"

        // field
        const val CATEGORY_ID = "category_id"
        const val QUIZ_ID = "quiz_id"
        const val QUESTION_ID = "question_id"
        const val USER_ANSWER = "user_answers"

        // bundle
        const val CATEGORY_BUNDLE = "category"
        const val CATEGORY_ID_BUNDLE = "category_id"
        const val RESULT_QUIZ_BUNDLE = "result_quiz"
        const val RESULT_QUIZ_TAG_BUNDLE = "result_quiz_tag"
        const val RESULT_QUIZ_EXAM_BUNDLE = "result_quiz_exam"
        const val RESULT_QUIZ_DETAIL_BUNDLE = "result_quiz_detail"
        const val TOTAL_QUESTION_BUNDLE = "total_question"
        const val QUIZ_ID_BUNDLE = "quiz_id"
        const val CATEGORY_TITLE_BUNDLE = "category_title"
        const val GET_QUIZ_QUESTION_BUNDLE = "get_quiz_question"
        const val GET_QUIZ_QUESTION_ID_BUNDLE = "get_quiz_question_id"
        const val BUNDLE_CURRENT_POSITION = "currentPosition"
        const val BUNDLE_PATH = "path"

        // state format
        const val FORMAT_TEXT = "text"
        const val FORMAT_VIDEO = "video"
        const val FORMAT_AUDIO = "audio"
        const val FORMAT_IMAGE = "image"
        const val MIM_TYPE_VIDEO = ".mp4"
        const val MIM_TYPE_AUDIO = ".mp3"
        const val ZERO_TIME = "00:00"


        // slider
        var DELAYED_SLIDER = 5000L
        var ZERO_ID_QUESTION = 0

        // videoView
        const val STATE_IDLE = 0
        const val STATE_PREPARING = 1
        const val STATE_PREPARED = 2
        const val STATE_PLAYING = 3
        const val STATE_PAUSED = 4
        const val STATE_PLAYBACK_COMPLETED = 5

        //posted_delay
        const val EVERY_SECOND = 1000L

        // requestCode
        const val REQUEST_CODE_FULL_SCREEN = 100
        const val REQUEST_PERMISSION_CODE_RECORD_AUDIO = 200

    }
}