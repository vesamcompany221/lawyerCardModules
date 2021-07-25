package com.vesam.barexamlibrary.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.get_category_list.Category
import com.vesam.barexamlibrary.data.model.response.get_category_list.Quizze
import com.vesam.barexamlibrary.data.model.response.get_category_list.ResponseGetCategoryListModel
import com.vesam.barexamlibrary.databinding.FragmentCategoryBinding
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import com.vesam.barexamlibrary.ui.view.adapter.category_list.CategoryAdapter
import com.vesam.barexamlibrary.ui.view.adapter.test_list.TestAdapter
import com.vesam.barexamlibrary.ui.viewmodel.QuizViewModel
import com.vesam.barexamlibrary.utils.application.AppQuiz
import com.vesam.barexamlibrary.utils.build_config.BuildConfig
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CATEGORY_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CATEGORY_ID_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CATEGORY_TITLE_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_API_TOKEN_VALUE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_UUID_VALUE
import com.vesam.barexamlibrary.utils.tools.HandelErrorTools
import com.vesam.barexamlibrary.utils.tools.ThrowableTools
import com.vesam.barexamlibrary.utils.tools.ToastTools
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment(), View.OnKeyListener {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var navController: NavController
    private val categoryAdapter: CategoryAdapter by inject()
    private val testAdapter: TestAdapter by inject()
    private val toastTools: ToastTools by inject()
    private val throwableTools: ThrowableTools by inject()
    private val handelErrorTools: HandelErrorTools by inject()
    private val quizViewModel: QuizViewModel by viewModel()
    private var isReset: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
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
        initAdapter()
        initOnClick()
        initRequestGetCategoryList()
        initOnBackPress()
    }

    private fun initNavController() {
        navController = Navigation.findNavController(AppQuiz.activity, R.id.my_nav_fragment)
    }

    private fun initOnBackPress() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener(this)
    }

    private fun initOnClick() {
        binding.imgReset.setOnClickListener { initRequestGetCategoryList() }
    }

    private fun initTestAdapter() {
        binding.rcTest.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rcTest.setHasFixedSize(true)
        binding.rcTest.adapter = testAdapter
        testAdapter.setOnItemClickListener(object : OnClickListenerAny {
            override fun onClickListener(any: Any) = initItemTestClickListener(any)
        })
    }

    private fun initItemTestClickListener(any: Any) {
        val quizze: Quizze = any as Quizze
        navController.navigate(R.id.quizDetailsFragment, initBundle(quizze))
    }

    private fun initRequestGetCategoryList() {
        isReset = false
        initShowLoading()
        quizViewModel.initGetCategoryList(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE
        ).observe(
                viewLifecycleOwner,
                this::initResultGetCategoryList
        )
    }

    private fun initResultGetCategoryList(it: Any) {
        initHideLoading()
        when (it) {
            is ResponseGetCategoryListModel -> initGetCategoryListModel(it)
            is Throwable -> initThrowable(it)
        }
    }

    private fun initGetCategoryListModel(it: ResponseGetCategoryListModel) {
        initCategory(it.categories)
        initQuizzes(it.quizzes)
        initCheckLists(it.categories.isEmpty(), it.quizzes.isEmpty())
    }

    private fun initCheckLists(emptyCategories: Boolean, emptyQuizzes: Boolean) {
        if (emptyCategories && emptyQuizzes) {
            binding.lnParent.visibility = View.GONE
            binding.lnNoResult.visibility = View.VISIBLE
        }
    }

    private fun initCategory(categories: List<Category>) {
        categoryAdapter.updateList(categories)
        initShowCategoryList(categories.isEmpty())
    }

    private fun initQuizzes(quizzes: List<Quizze>) {
        testAdapter.updateList(quizzes)
        initShowQuizzesList(quizzes.isEmpty())

    }

    private fun initShowCategoryList(empty: Boolean) = when {
        empty -> {
            binding.rcCategory.visibility = View.GONE
            binding.txtQuestionList.visibility = View.GONE
            binding.vMargin32.visibility = View.GONE
            binding.vMargin32.visibility = View.GONE
        }
        else -> {
            binding.rcCategory.visibility = View.VISIBLE
            binding.txtQuestionList.visibility = View.VISIBLE
            binding.vMargin32.visibility = View.VISIBLE
            binding.vMargin32.visibility = View.VISIBLE
        }
    }

    private fun initShowQuizzesList(empty: Boolean) = when {
        empty -> {
            binding.rcTest.visibility = View.GONE
            binding.txtQuestionList.visibility = View.GONE
        }
        else -> {
            binding.rcTest.visibility = View.VISIBLE
            binding.txtQuestionList.visibility = View.VISIBLE
        }
    }

    private fun initShowLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.lnParent.visibility = View.GONE
        binding.lnNoResult.visibility = View.GONE
    }

    private fun initHideLoading() {
        binding.lnParent.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun initThrowable(it: Throwable) {
        val message = throwableTools.getThrowableError(it)
        handelErrorTools.handelError(it)
        toastTools.toast(message)
    }

    private fun initAdapter() {
        initCategoryAdapter()
        initTestAdapter()
    }

    private fun initCategoryAdapter() {
        binding.rcCategory.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rcCategory.setHasFixedSize(true)
        binding.rcCategory.adapter = categoryAdapter
        categoryAdapter.setOnItemClickListener(object : OnClickListenerAny {
            override fun onClickListener(any: Any) = initItemCategoryClickListener(any)
        })
    }

    private fun initItemCategoryClickListener(any: Any) {
        isReset = true
        val category: Category = any as Category
        initShowLoading()
        quizViewModel.initGetCategoryList(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                category.id
        ).observe(
                viewLifecycleOwner,
                this::initResultGetCategoryList
        )
    }

    private fun initBundle(quizze: Quizze): Bundle {
        val bundle = Bundle()
        bundle.putInt(CATEGORY_ID_BUNDLE, quizze.id)
        return bundle
    }

    private fun initToolbar() {
        initAppCompatActivityToolbar()
    }

    private fun initAppCompatActivityToolbar() {
        (AppQuiz.activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (AppQuiz.activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
        binding.toolbar.setNavigationOnClickListener { initStateBack() }
    }

    private fun initStateBack() = when {
        isReset -> initRequestGetCategoryList()
        else -> initFinish()
    }

    private fun initFinish() {
        AppQuiz.activity.finish()
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                initOnBackPressed()
                return true
            }
        }
        return false
    }

    private fun initOnBackPressed() {
        AppQuiz.activity.finish()
    }


}