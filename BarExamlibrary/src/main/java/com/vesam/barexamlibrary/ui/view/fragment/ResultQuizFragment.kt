package com.vesam.barexamlibrary.ui.view.fragment

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.set_quiz_question.ResponseSetQuizQuestionModel
import com.vesam.barexamlibrary.databinding.FragmentResultQuizBinding
import com.vesam.barexamlibrary.utils.application.AppQuiz
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CATEGORY_ID_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_DETAIL_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_TAG_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.TOTAL_QUESTION_BUNDLE
import com.vesam.barexamlibrary.utils.extention.toPersianNumber
import com.vesam.barexamlibrary.utils.tools.HandelErrorTools
import org.koin.android.ext.android.inject


class ResultQuizFragment : Fragment() {

    private lateinit var binding: FragmentResultQuizBinding
    private val handelErrorTools: HandelErrorTools by inject()
    private lateinit var navController: NavController
    private val gson: Gson by inject()
    private var categoryId = -1
    private var questionCount = -1


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentResultQuizBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initAction()
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    private fun initAction() {
        initNavController()
        initBundle()
        initOnClick()
    }

    private fun initNavController() {
        navController = Navigation.findNavController(AppQuiz.activity, R.id.my_nav_fragment)
    }

    private fun initOnClick() {
        binding.btnReturn.setOnClickListener { initReturn() }
        binding.btnRetry.setOnClickListener { initRetry() }
    }

    private fun initRetry() {
        navController.popBackStack()
        navController.navigate(R.id.examFragment, initRetryBundle())
    }

    private fun initRetryBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt(CATEGORY_ID_BUNDLE, categoryId)
        bundle.putInt(TOTAL_QUESTION_BUNDLE, questionCount)
        return bundle
    }

    private fun initReturn() {
        initPopBackStack()
        navController.navigate(R.id.quizDetailsFragment, initReturnBundle())
    }

    private fun initPopBackStack() {
        val resultQuizTag = requireArguments().getString(RESULT_QUIZ_TAG_BUNDLE, "")
        when {
            resultQuizTag.equals(RESULT_QUIZ_DETAIL_BUNDLE) -> {
                navController.popBackStack()
                navController.popBackStack()
            }
            else -> {
                navController.popBackStack()
                navController.popBackStack()
            }
        }
    }

    private fun initReturnBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt(CATEGORY_ID_BUNDLE, categoryId)
        return bundle
    }

    private fun initBundle() {
        val strResultQuiz = requireArguments().getString(RESULT_QUIZ_BUNDLE, "")
        val responseSetQuizQuestionModel =
                gson.fromJson(strResultQuiz, ResponseSetQuizQuestionModel::class.java)
        initSetView(responseSetQuizQuestionModel)
    }

    @SuppressLint("SetTextI18n")
    private fun initSetView(it: ResponseSetQuizQuestionModel) {
        initPercentToPassed(it.quiz.result.userPercent)
        initPassed(it.quiz.result.passed)
        categoryId = it.quiz.id
        questionCount = it.quiz.questionCount
        initHtmlText(it.quiz.giftDescription)
        initCheckGiftChargeWallet(it.quiz.giftChargeWallet)
    }

    private fun initPassed(passed: Boolean) {
        when {
            passed -> binding.txtTitle.text = resources.getString(R.string.accept_quiz)
            else -> binding.txtTitle.text = resources.getString(R.string.not_accept_quiz)
        }
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.damavand_extra_bold)
        binding.txtTitle.typeface = typeface
        binding.txtPercentToPassed.typeface = typeface
        binding.txtGiftChargeWallet.typeface = typeface

    }

    @SuppressLint("SetTextI18n")
    private fun initPercentToPassed(userPercent: Int) {
        val animator = ValueAnimator.ofInt(0, userPercent)
        animator.addUpdateListener { animation ->
            val animateValue = animation.animatedValue as Int
            if (userPercent == 0) {
                binding.progressBarPercentToPassed.setProgress(animateValue)
                binding.txtPercentToPassed.text = "% ${toPersianNumber(animateValue.toString())}"
                animator.cancel()
            } else {
                val progress = (animation.animatedValue as Int) + 1
                binding.progressBarPercentToPassed.setProgress(progress)
                binding.txtPercentToPassed.text = "% ${toPersianNumber(progress.toString())}"
                if (userPercent == progress) animator.cancel()
            }
        }
        animator.repeatCount = ValueAnimator.INFINITE
        animator.duration = 400
        animator.start()
    }



    private fun initHtmlText(description: String) {
        val plainText = Html.fromHtml(description).toString()
        binding.txtGiftDescription.text = plainText
    }

    @SuppressLint("SetTextI18n")
    private fun initCheckGiftChargeWallet(giftChargeWallet: Int) {
        if (giftChargeWallet == 0) {
            binding.lnGiftChargeWallet.visibility = View.GONE
        } else {
            binding.txtGiftChargeWallet.text = "مبلغ $giftChargeWallet تومان کیف پول شما شارژ شد"
            binding.lnGiftChargeWallet.visibility = View.VISIBLE
        }
    }

}