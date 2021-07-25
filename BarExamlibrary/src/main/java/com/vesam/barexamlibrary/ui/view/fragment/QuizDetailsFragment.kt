package com.vesam.barexamlibrary.ui.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.buy_wallet.ResponseBuyWalletModel
import com.vesam.barexamlibrary.data.model.response.get_category_detail.ResponseGetCategoryDetailModel
import com.vesam.barexamlibrary.data.model.response.online_payment.ResponseOnlinePaymentModel
import com.vesam.barexamlibrary.data.model.response.set_quiz_question.ResponseSetQuizQuestionModel
import com.vesam.barexamlibrary.databinding.FragmentQuizDetailsBinding
import com.vesam.barexamlibrary.ui.viewmodel.QuizViewModel
import com.vesam.barexamlibrary.utils.application.AppQuiz
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CATEGORY_ID_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_DETAIL_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_TAG_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.TOTAL_QUESTION_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_API_TOKEN_VALUE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_UUID_VALUE
import com.vesam.barexamlibrary.utils.tools.GlideTools
import com.vesam.barexamlibrary.utils.tools.HandelErrorTools
import com.vesam.barexamlibrary.utils.tools.ThrowableTools
import com.vesam.barexamlibrary.utils.tools.ToastTools
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class QuizDetailsFragment : Fragment() {

    private lateinit var binding: FragmentQuizDetailsBinding
    private lateinit var navController: NavController
    private val toastTools: ToastTools by inject()
    private val gson: Gson by inject()
    private val throwableTools: ThrowableTools by inject()
    private val handelErrorTools: HandelErrorTools by inject()
    private val glideTools: GlideTools by inject()
    private val quizViewModel: QuizViewModel by viewModel()
    private var categoryId = -1
    private var questionCount = -1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuizDetailsBinding.inflate(layoutInflater)
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
        initToolbar()
        initOnClick()
        initBundle()
        initRequestDetailCategory()
    }

    private fun initNavController() {
        navController = Navigation.findNavController(AppQuiz.activity, R.id.my_nav_fragment)
    }

    private fun initRequestDetailCategory() {
        initShowLoading()
        quizViewModel.initGetCategoryDetail(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId
        ).observe(
                viewLifecycleOwner,
                this::initResultGetCategoryDetail
        )
    }

    private fun initResultGetCategoryDetail(it: Any) {
        initHideLoading()
        when (it) {
            is ResponseGetCategoryDetailModel -> initGetCategoryDetailModel(it)
            is Throwable -> initThrowable(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initGetCategoryDetailModel(it: ResponseGetCategoryDetailModel) {
        questionCount = it.details.questionCount
        binding.txtTitle.text = it.details.title
        binding.txtCategoryTitle.text = it.details.category.title
        binding.txtAmount.text = initPrice(it.details.price)
        initHtmlText(it.details.description)
        initCheckPrice(it.details.price)
        initUserAlreadyTakenQuiz(it.details.userAlreadyTakenQuiz)
        glideTools.displaySliderImage(
                binding.image,
                it.details.slideImage,
                R.drawable.shape_round_slider_place_holder,
                R.drawable.shape_round_slider_place_holder
        )
    }

    private fun initUserAlreadyTakenQuiz(userAlreadyTakenQuiz: Boolean) {
        if (userAlreadyTakenQuiz) {
            binding.lnUserAlreadyTakenQuiz.visibility = View.VISIBLE
            binding.lnStartTest.visibility = View.GONE
            binding.lnPayment.visibility = View.GONE
        }
    }

    private fun initCheckPrice(price: Int) {
        when {
            price > 0 -> initUnPaid()
            price == 0 -> initFree()
            else -> initPaid()
        }
    }

    private fun initPaid() {
        binding.lnPayment.visibility = View.GONE
        binding.lnStartTest.visibility = View.VISIBLE
    }

    private fun initFree() {
        binding.lnPayment.visibility = View.GONE
        binding.lnStartTest.visibility = View.VISIBLE
    }

    private fun initUnPaid() {
        binding.lnPayment.visibility = View.VISIBLE
        binding.lnStartTest.visibility = View.GONE
    }

    private fun initPrice(price: Int): String = when {
        price > 0 -> "$price ${resources.getString(R.string.unit_money)}"
        price == 0 -> resources.getString(R.string.free)
        else -> resources.getString(R.string.paid)
    }

    private fun initHtmlText(description: String) {
        val plainText = Html.fromHtml(description).toString()
        binding.txtDescription.text = plainText
    }

    private fun initThrowable(it: Throwable) {
        val message = throwableTools.getThrowableError(it)
        handelErrorTools.handelError(it)
        toastTools.toast(message)
    }

    private fun initShowLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.lnParent.visibility = View.GONE
    }

    private fun initHideLoading() {
        binding.lnParent.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun initBundle() {
        categoryId = requireArguments().getInt(CATEGORY_ID_BUNDLE, -1)
    }

    private fun initOnClick() {
        binding.txtRetest.setOnClickListener {
            navController.navigate(
                    R.id.examFragment,
                    initTotalQuestionBundle()
            )
        }
        binding.lnPayment.setOnClickListener { initPay() }
        binding.txtRetest.setOnClickListener {
            navController.navigate(
                    R.id.examFragment,
                    initTotalQuestionBundle()
            )
        }
        binding.lnStartTest.setOnClickListener {
            navController.navigate(
                    R.id.examFragment,
                    initTotalQuestionBundle()
            )
        }
        binding.txtTheResultOfTheLastTest.setOnClickListener { initRequestTheResultOfTheLastTest() }
    }

    private fun initRequestTheResultOfTheLastTest() {
        initShowLoadingTheResultOfTheLastTest()
        quizViewModel.initGetQuizQuestionResult(USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId).observe(viewLifecycleOwner, this::initResultTheResultOfTheLastTest)
    }

    private fun initResultTheResultOfTheLastTest(it: Any) {
        initHideLoadingTheResultOfTheLastTest()
        when (it) {
            is ResponseSetQuizQuestionModel -> initSetQuizQuestionModel(it)
            is Throwable -> initThrowable(it)
        }
    }

    private fun initSetQuizQuestionModel(it: ResponseSetQuizQuestionModel) {
        navController.popBackStack()
        navController.navigate(R.id.resultQuizFragment, initResultQuizBundle(it))
    }

    private fun initResultQuizBundle(it: ResponseSetQuizQuestionModel): Bundle {
        val bundle = Bundle()
        bundle.putString(RESULT_QUIZ_BUNDLE, gson.toJson(it))
        bundle.putString(RESULT_QUIZ_TAG_BUNDLE, RESULT_QUIZ_DETAIL_BUNDLE)
        return bundle
    }

    private fun initShowLoadingTheResultOfTheLastTest() {
        binding.lnProgressTheResultOfTheLastTest.visibility=View.VISIBLE
        binding.txtTheResultOfTheLastTest.visibility=View.GONE
    }

    private fun initHideLoadingTheResultOfTheLastTest() {
        binding.lnProgressTheResultOfTheLastTest.visibility=View.GONE
        binding.txtTheResultOfTheLastTest.visibility=View.VISIBLE
    }

    private fun initPay() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_peyment)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val cvPay=dialog.findViewById<CardView>(R.id.CvPay)
        val cvWallet=dialog.findViewById<CardView>(R.id.CvWallet)
        val llPay=dialog.findViewById<View>(R.id.llPay)
        val avlPay=dialog.findViewById<View>(R.id.avlPay)
        val llWallet=dialog.findViewById<View>(R.id.llWallet)
        val avlWallet=dialog.findViewById<View>(R.id.avlWallet)
        cvPay.setBackgroundColor(resources.getColor(R.color.white))
        cvWallet.setBackgroundColor(resources.getColor(R.color.white))
        llPay.setOnClickListener { initOnline(dialog, llPay, avlPay) }
        llWallet.setOnClickListener { initWallet(dialog, llWallet, avlWallet) }
        dialog.show()
    }

    private fun initWallet(dialog: Dialog, llWallet: View, avlWallet: View) {
        initShowLoadingWallet(llWallet,avlWallet)
        quizViewModel.initBuyWallet(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId
        ).observe(viewLifecycleOwner, {initResultBuyWallet(it,dialog, llWallet, avlWallet)})
    }

    private fun initShowLoadingWallet(llWallet: View, avlWallet: View) {
        llWallet.visibility=View.INVISIBLE
        avlWallet.visibility=View.VISIBLE
    }

    private fun initShowLoadingOnline(llPay: View, avlPay: View) {
        llPay.visibility=View.INVISIBLE
        avlPay.visibility=View.VISIBLE
    }

    private fun initOnline(dialog: Dialog, llPay: View, avlPay: View) {
        initShowLoadingOnline(llPay,avlPay)
        quizViewModel.initOnlinePayment(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId
        ).observe(viewLifecycleOwner, {initResultOnlinePayment(it,dialog,llPay,avlPay)})
    }

    private fun initResultBuyWallet(it: Any,dialog: Dialog, llWallet: View, avlWallet: View) {
        dialog.dismiss()
        initHideLoadingWallet(llWallet,avlWallet)
        when (it) {
            is ResponseBuyWalletModel -> initResultBuyWalletModel(it)
            is Throwable -> initThrowable(it)
        }
    }

    private fun initHideLoadingWallet(llWallet: View, avlWallet: View) {
        llWallet.visibility=View.VISIBLE
        avlWallet.visibility=View.INVISIBLE
    }

    private fun initResultOnlinePayment(it: Any, dialog: Dialog, llPay: View, avlPay: View) {
        dialog.dismiss()
        initHideLoadingOnline(llPay,avlPay)
        when (it) {
            is ResponseOnlinePaymentModel -> initOnlinePaymentModel(it)
            is Throwable -> initThrowable(it)
        }
    }

    private fun initHideLoadingOnline(llPay: View, avlPay: View) {
        llPay.visibility=View.VISIBLE
        avlPay.visibility=View.INVISIBLE
    }

    private fun initResultBuyWalletModel(it: ResponseBuyWalletModel) {
        when {
            it.status -> {
                binding.lnPayment.visibility=View.GONE
                binding.lnStartTest.visibility=View.VISIBLE
            }
        }
    }

    private fun initOnlinePaymentModel(it: ResponseOnlinePaymentModel) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(it.url))
    }

    private fun initTotalQuestionBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt(CATEGORY_ID_BUNDLE, categoryId)
        bundle.putInt(TOTAL_QUESTION_BUNDLE, questionCount)
        return bundle
    }

    private fun initToolbar() {
        initAppCompatActivityToolbar()
    }

    private fun initAppCompatActivityToolbar() {
        (AppQuiz.activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (AppQuiz.activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (AppQuiz.activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_toolbar_white)
        binding.toolbar.setNavigationOnClickListener { initFinish() }
    }

    private fun initFinish() {
        navController.popBackStack()
    }

}
