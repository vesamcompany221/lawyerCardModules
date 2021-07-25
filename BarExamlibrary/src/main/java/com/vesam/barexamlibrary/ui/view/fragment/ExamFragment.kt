@file:Suppress("NAME_SHADOWING")

package com.vesam.barexamlibrary.ui.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.session.PlaybackState.STATE_PAUSED
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.response.get_quiz_question.Answer
import com.vesam.barexamlibrary.data.model.response.get_quiz_question.ResponseGetQuizQuestionModel
import com.vesam.barexamlibrary.data.model.response.set_quiz_question.ResponseSetQuizQuestionModel
import com.vesam.barexamlibrary.databinding.FragmentExamBinding
import com.vesam.barexamlibrary.interfaces.OnClickListener
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import com.vesam.barexamlibrary.ui.view.activity.FullScreenActivity
import com.vesam.barexamlibrary.ui.view.adapter.answer_list.AnswerAdapter
import com.vesam.barexamlibrary.ui.viewmodel.QuizViewModel
import com.vesam.barexamlibrary.utils.application.AppQuiz
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.BUNDLE_CURRENT_POSITION
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.BUNDLE_PATH
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.CATEGORY_ID_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.EVERY_SECOND
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.FORMAT_AUDIO
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.FORMAT_TEXT
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.FORMAT_VIDEO
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.REQUEST_CODE_FULL_SCREEN
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.REQUEST_PERMISSION_CODE_RECORD_AUDIO
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_DETAIL_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.RESULT_QUIZ_TAG_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.TOTAL_QUESTION_BUNDLE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_API_TOKEN_VALUE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.USER_UUID_VALUE
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.ZERO_ID_QUESTION
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.ZERO_TIME
import com.vesam.barexamlibrary.utils.extention.initConvertMillieToHMmSs
import com.vesam.barexamlibrary.utils.manager.DialogManager
import com.vesam.barexamlibrary.utils.tools.GlideTools
import com.vesam.barexamlibrary.utils.tools.HandelErrorTools
import com.vesam.barexamlibrary.utils.tools.ThrowableTools
import com.vesam.barexamlibrary.utils.tools.ToastTools
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import rm.com.audiowave.OnProgressListener
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class ExamFragment : Fragment(), View.OnKeyListener {

    private lateinit var binding: FragmentExamBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var timer: Timer
    private var userAnswers: ArrayList<Int> = ArrayList()
    private val throwableTools: ThrowableTools by inject()
    private lateinit var navController: NavController
    private val toastTools: ToastTools by inject()
    private val glideTools: GlideTools by inject()
    private val dialogManager: DialogManager by inject()
    private val gson: Gson by inject()
    private val handelErrorTools: HandelErrorTools by inject()
    private val answerAdapter: AnswerAdapter by inject()
    private val quizViewModel: QuizViewModel by viewModel()
    private var categoryId = -1
    private var totalQuestion = -1
    private var nextQuestionId = -1
    private var previewQuestionId = -1
    private var questionSort = -1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExamBinding.inflate(layoutInflater)
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

    override fun onPause() {
        super.onPause()
        initPauseVideo()
        initStopSoundView()
    }

    private fun initAction() {
        initNavController()
        initToolbar()
        initAnswersAdapter()
        initBundle()
        initOnClick()
        initWaveByte()
        initOnSeekBarChangeListener()
        initRequestGetQuizQuestion()
        initOnBackPress()
    }

    private fun initToolbar() {
        initAppCompatActivityToolbar()
    }

    private fun initAppCompatActivityToolbar() {
        (AppQuiz.activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (AppQuiz.activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (AppQuiz.activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_toolbar_white)
        binding.toolbar.setNavigationOnClickListener { initDialog() }
    }

    private fun initWaveByte() {
        binding.lnExam.visualizer.onProgressListener = object : OnProgressListener {
            override fun onStartTracking(progress: Float) {}
            override fun onStopTracking(progress: Float) {}
            override fun onProgressChanged(progress: Float, byUser: Boolean) {
                if (byUser) if (::mediaPlayer.isInitialized) mediaPlayer.seekTo((progress * 1000).toInt())
            }
        }
    }

    private fun getFileInformation(): ByteArray {
        return byteArrayOf(96, 0, 15, 91, 49, 77, 1, 1, 122, 96, 10, 38, 121, 14, 37, 29, 92, 18, 24, 21, 64, 6, 61, 25, 10, 95, 25, 21, 19, 19, 89, 113, 14, 34, 92, 105, 65, 49, 22, 120, 3, 18, 124, 2, 45, 23, 90, 32, 0, 122, 1, 14, 13, 99, 36, 21, 123, 18, 32, 60, 87, 98, 78, 31, 13, 72, 38, 5, 76, 18, 65, 67, 12, 20, 15, 15, 105, 51, 91, 8, 54, 78, 105, 62, 63, 50, 16, 78, 9, 56, 66, 25, 51, 95, 55, 5, 67, 65, 99, 57, 126, 113, 71, 37, 28, 71, 101, 37, 20, 67, 9, 11, 117, 15, 108, 33, 10, 5, 99, 60, 102, 68, 49, 117, 25, 24, 34, 60, 112, 126, 93, 7, 30, 65, 18, 124, 2, 45, 23, 90, 32, 0, 122, 1, 14, 13, 99, 36, 21, 123, 18, 32, 60, 87, 98, 78, 31, 13, 72, 38, 5, 76, 18, 65, 67)
    }

    private fun initNavController() {
        navController = Navigation.findNavController(AppQuiz.activity, R.id.my_nav_fragment)
    }

    private fun initOnBackPress() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener(this)
    }

    private fun initOnSeekBarChangeListener() {
        binding.lnExam.seekBarTime.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) binding.lnExam.video.seekTo(seekBar!!.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }


    private fun initOnClick() {
        binding.lnExam.txtPreviewQuestionId.setOnClickListener { initCheckPreviewQuestionId() }
        binding.lnExam.txtNextQuestionId.setOnClickListener { initCheckNextQuestionId() }
        binding.lnExam.imgBtnFullScreen.setOnClickListener { initFullScreen() }
        binding.lnExam.image.setOnClickListener { initFullScreen() }
        binding.lnExam.imgPlay.setOnClickListener { initPlayVideo() }
        binding.lnExam.imgPause.setOnClickListener { initPauseVideo() }
        binding.lnExam.imgBtnVideoFullScreen.setOnClickListener { initVideoFullScreen() }
        binding.lnExam.imgPlaySound.setOnClickListener { initPlaySound() }
        binding.lnExam.imgPauseSound.setOnClickListener { initPauseSoundView() }
    }

    private fun initPauseSoundView() {
        initShowPauseSound()
        initPauseSound()
    }

    private fun initStopSoundView() {
        initShowPauseSound()
        initStopSound()
    }

    private fun initPlaySound() {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { initOnCompletionListenerSound() }
            initShowPlaySound()
            initDurationSound()
            initTimerSound()
        }
    }

    private fun initTimerSound() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            var updateUI =
                    Runnable {
                        binding.lnExam.txtStartTimeSound.text =
                                initConvertMillieToHMmSs(mediaPlayer.currentPosition.toLong())
                        binding.lnExam.visualizer.progress = ((mediaPlayer.currentPosition * 100 / mediaPlayer.duration).toFloat())

                    }

            override fun run() {
                if (activity == null)
                    return
                requireActivity().runOnUiThread(updateUI)
            }
        }, 0, 50)

    }

    private fun initOnCompletionListenerSound() {
        initShowPauseSound()
    }

    private fun initShowPlaySound() {
        binding.lnExam.imgPlaySound.visibility = View.GONE
        binding.lnExam.imgPauseSound.visibility = View.VISIBLE
    }

    private fun initShowPauseSound() {
        binding.lnExam.visualizer.onStopTracking
        binding.lnExam.imgPlaySound.visibility = View.VISIBLE
        binding.lnExam.imgPauseSound.visibility = View.GONE
    }

    private fun initPauseSound() {
        if (::mediaPlayer.isInitialized) {
            try {
                if (mediaPlayer.isPlaying)
                    mediaPlayer.pause()
            } catch (e: Exception) {
                handelErrorTools.handelError(e)
            }
        }
    }

    private fun initStopSound() {
        if (::mediaPlayer.isInitialized) {
            try {
                if (mediaPlayer.isPlaying)
                    mediaPlayer.pause()
            } catch (e: Exception) {
                handelErrorTools.handelError(e)
            }
        }
    }

    private fun initVideoFullScreen() {
        val intent = Intent()
        intent.putExtra(BUNDLE_CURRENT_POSITION, initCurrentPosition())
        intent.putExtra(BUNDLE_PATH, initPath())
        intent.setClass(requireActivity(), FullScreenActivity::class.java)
        requireActivity().startActivityForResult(intent, REQUEST_CODE_FULL_SCREEN)
    }

    private fun initCurrentPosition(): Int = binding.lnExam.video.currentPosition

    private fun initPath(): String = binding.lnExam.video.tag as String

    private fun initPlayVideo() {
        initCheckPlay()
        initShowPlayView()
        initResetStartTime()
        initResetSeekBar()
    }

    private fun initCheckPlay() = when (binding.lnExam.video.currentPosition) {
        STATE_PAUSED -> binding.lnExam.video.resume()
        else -> binding.lnExam.video.start()
    }

    private fun initPauseVideo() {
        if (binding.lnExam.video.isPlaying) {
            binding.lnExam.video.pause()
            initShowPauseView()
        }
    }

    private fun initShowPlayView() {
        binding.lnExam.imgPlay.visibility = View.GONE
        binding.lnExam.lnController.visibility = View.VISIBLE
        binding.lnExam.imgBtnVideoFullScreen.visibility = View.VISIBLE
    }

    private fun initShowPauseView() {
        binding.lnExam.imgPlay.visibility = View.VISIBLE
        binding.lnExam.lnController.visibility = View.GONE
        binding.lnExam.imgBtnVideoFullScreen.visibility = View.GONE
    }

    private fun initFullScreen() {
        val urlContent: String = binding.lnExam.image.tag as String
        val fragmentManager: FragmentManager =
                (AppQuiz.activity as AppCompatActivity).supportFragmentManager
        val fragmentFullscreenSlider = FragmentFullscreenSliderImage()
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, fragmentFullscreenSlider).addToBackStack(null)
                .commit()
        fragmentFullscreenSlider.setImage(urlContent)
    }

    private fun initCheckPreviewQuestionId() {
        if (questionSort > 0) initPreviewQuestionId()
    }

    private fun initCheckNextQuestionId() = when {
        nextQuestionId > 0 -> initNextQuestionId()
        else -> initRequestResultQuestion()
    }

    private fun initRequestResultQuestion() {
        quizViewModel.initSetQuizQuestion(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId, getStrResultQuestion(userAnswers)
        ).observe(viewLifecycleOwner, this::initResultSetQuizQuestion)
    }

    private fun getStrResultQuestion(userAnswers: ArrayList<Int>) = gson.toJson(userAnswers)

    private fun initResultSetQuizQuestion(it: Any) {
        initHideLoading()
        when (it) {
            is ResponseSetQuizQuestionModel -> initSetQuizQuestionModel(it)
            is Throwable -> initThrowable(it)
        }
    }

    private fun initSetQuizQuestionModel(it: ResponseSetQuizQuestionModel) {
        if (::timer.isInitialized)
            timer.cancel()
        navController.popBackStack()
        navController.navigate(R.id.resultQuizFragment, initResultQuizBundle(it))
    }

    private fun initResultQuizBundle(it: ResponseSetQuizQuestionModel): Bundle {
        val bundle = Bundle()
        bundle.putString(RESULT_QUIZ_BUNDLE, gson.toJson(it))
        bundle.putString(RESULT_QUIZ_TAG_BUNDLE, RESULT_QUIZ_DETAIL_BUNDLE)
        return bundle
    }

    private fun initNextQuestionId() {
        initPauseVideo()
        initPauseSound()
        initShowLoading()
        quizViewModel.initGetQuizQuestion(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId, nextQuestionId
        ).observe(
                viewLifecycleOwner,
                this::initResultGetQuizQuestion
        )
    }

    private fun initPreviewQuestionId() {
        initRemoveLastIndex()
        initPauseVideo()
        initPauseSoundView()
        initShowLoading()
        quizViewModel.initGetQuizQuestion(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId, previewQuestionId
        ).observe(
                viewLifecycleOwner,
                this::initResultGetQuizQuestion
        )
    }

    private fun initRemoveLastIndex() {
        if (userAnswers.isNotEmpty())
            userAnswers.removeAt(userAnswers.lastIndex)
    }

    private fun initRequestGetQuizQuestion() {
        initShowLoading()
        quizViewModel.initGetQuizQuestion(
                USER_UUID_VALUE,
                USER_API_TOKEN_VALUE,
                categoryId, ZERO_ID_QUESTION
        ).observe(
                viewLifecycleOwner,
                this::initResultGetQuizQuestion
        )
    }

    private fun initResultGetQuizQuestion(it: Any) {
        when (it) {
            is ResponseGetQuizQuestionModel -> initGetQuizQuestionModel(it)
            is Throwable -> initThrowable(it)
        }
    }

    private fun initThrowable(it: Throwable) {
        initHideLoadingProgress()
        val message = throwableTools.getThrowableError(it)
        handelErrorTools.handelError(it)
        toastTools.toast(message)
    }

    private fun initHideLoadingProgress() {
        binding.lnExam.progressBar.visibility = View.GONE
    }

    private fun initShowLoading() {
        binding.lnExam.lnParent.visibility = View.GONE
        binding.lnExam.lnNoResult.visibility = View.GONE
        binding.lnExam.progressBar.visibility = View.VISIBLE
    }

    private fun initHideLoading() {
        binding.lnExam.lnParent.visibility = View.VISIBLE
        binding.lnExam.progressBar.visibility = View.GONE
    }

    private fun initGetQuizQuestionModel(it: ResponseGetQuizQuestionModel) {
        initHideLoading()
        initUpdateAdapter(it)
        initCountAnswer(it)
        initPreviewAndNext(it)
        initCheckFinal()
        initCheckVisiblePreviewQuestionId()
        when (it.question.description.format) {
            FORMAT_TEXT -> initQuestionFormatText(it)
            FORMAT_VIDEO -> initQuestionFormatVideo(it)
            FORMAT_AUDIO -> initQuestionFormatAudio(it)
            else -> initQuestionFormatImage(it)
        }
    }

    private fun initCheckFinal() {
        if (nextQuestionId == 0)
            binding.lnExam.txtNextQuestionId.text = resources.getString(R.string.result)
    }

    private fun initCheckVisiblePreviewQuestionId() {
        if (previewQuestionId == 0) {
            binding.lnExam.txtPreviewQuestionId.visibility = View.INVISIBLE
            binding.lnExam.txtPreviewQuestionId.isEnabled = false
        } else {
            binding.lnExam.txtPreviewQuestionId.visibility = View.VISIBLE
            binding.lnExam.txtPreviewQuestionId.isEnabled = true
        }
    }

    private fun initPreviewAndNext(it: ResponseGetQuizQuestionModel) {
        previewQuestionId = it.question.previewQuestionId
        nextQuestionId = it.question.nextQuestionId
    }

    @SuppressLint("SetTextI18n")
    private fun initCountAnswer(it: ResponseGetQuizQuestionModel) {
        questionSort = it.question.sort
        binding.lnExam.txtCounter.text = questionSort.toString()
        binding.lnExam.txtTotal.text = totalQuestion.toString()
    }

    private fun initUpdateAdapter(it: ResponseGetQuizQuestionModel) {
        answerAdapter.updateList(it.question.answers)
        initCheckEmptyAdapter(it.question.answers.isEmpty())
    }

    private fun initCheckEmptyAdapter(empty: Boolean) = when {
        empty -> binding.lnExam.lnNoResult.visibility = View.VISIBLE
        else -> binding.lnExam.lnNoResult.visibility = View.GONE
    }

    private fun initQuestionFormatImage(it: ResponseGetQuizQuestionModel) {
        initShowItemImage()
        glideTools.displaySliderImage(
                binding.lnExam.image,
                it.question.description.content,
                R.drawable.shape_round_slider_place_holder,
                R.drawable.shape_round_slider_place_holder
        )
    }

    private fun initQuestionFormatAudio(it: ResponseGetQuizQuestionModel) {
        initShowItemSound()
        initSound(it.question.description.content)
    }

    private fun initSound(content: String) {
        releaseMPAnswer()
        binding.lnExam.visualizer.scaledData = getFileInformation()
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(
                    requireContext(), Uri.parse(
                    content
            )
            )
            mediaPlayer.prepare()
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    private fun initDurationSound() {
        binding.lnExam.txtEndTimeSound.text =
                initConvertMillieToHMmSs(mediaPlayer.duration.toLong())
    }

    private fun releaseMPAnswer() {
        if (::mediaPlayer.isInitialized) try {
            mediaPlayer.release()
        } catch (e: Exception) {
            handelErrorTools.handelError(e)
        }
    }

    private fun initQuestionFormatVideo(it: ResponseGetQuizQuestionModel) {
        initShowItemVideo()
        binding.lnExam.video.tag = it.question.description.content
        binding.lnExam.video.setVideoPath(it.question.description.content)
        binding.lnExam.video.setOnCompletionListener { initOnCompletionListener() }
        binding.lnExam.video.setOnPreparedListener(this::initOnPreparedListener)

    }

    private fun initOnCompletionListener() {
        initShowPauseView()
    }

    private fun initOnPreparedListener(it: MediaPlayer?) {
        initResetSeekBar()
        initDurationSeekBar()
        binding.lnExam.seekBarTime.postDelayed(onEverySecond, EVERY_SECOND)
        binding.lnExam.txtEndTime.text = initConvertMillieToHMmSs(it!!.duration.toLong())
    }

    private fun initDurationSeekBar() {
        binding.lnExam.seekBarTime.max = binding.lnExam.video.duration
    }

    private fun initResetStartTime() {
        binding.lnExam.txtStartTime.text = ZERO_TIME
    }

    private fun initResetSeekBar() {
        binding.lnExam.seekBarTime.progress = 0
    }


    private val onEverySecond: Runnable = object : Runnable {
        override fun run() {
            val currentPosition: Int = binding.lnExam.video.currentPosition
            binding.lnExam.seekBarTime.progress = currentPosition
            binding.lnExam.txtStartTime.text = initConvertMillieToHMmSs(currentPosition.toLong())
            if (binding.lnExam.video.isPlaying)
                binding.lnExam.seekBarTime.postDelayed(this, EVERY_SECOND)
        }
    }


    private fun initQuestionFormatText(it: ResponseGetQuizQuestionModel) {
        initShowItemText()
        initHtmlText(it.question.description.content)
    }

    private fun initHtmlText(description: String) {
        val plainText = Html.fromHtml(description).toString()
        binding.lnExam.txtTitle.text = plainText
    }

    private fun initShowItemText() {
        binding.lnExam.lnText.visibility = View.VISIBLE
        binding.lnExam.lnImage.visibility = View.GONE
        binding.lnExam.lnVideo.visibility = View.GONE
        binding.lnExam.lnSound.visibility = View.GONE
    }

    private fun initShowItemImage() {
        binding.lnExam.lnText.visibility = View.GONE
        binding.lnExam.lnVideo.visibility = View.GONE
        binding.lnExam.lnImage.visibility = View.VISIBLE
        binding.lnExam.lnSound.visibility = View.GONE
    }

    private fun initShowItemVideo() {
        lnShowVideo()
        binding.lnExam.lnVideo.visibility = View.VISIBLE
        binding.lnExam.lnText.visibility = View.GONE
        binding.lnExam.lnImage.visibility = View.GONE
        binding.lnExam.lnSound.visibility = View.GONE
    }

    private fun lnShowVideo() {
        binding.lnExam.progressBarVideo.visibility = View.VISIBLE
        binding.lnExam.rVideo.visibility = View.INVISIBLE
        binding.lnExam.lnVideo.postDelayed(onShowLayoutVideo, EVERY_SECOND)
    }

    private val onShowLayoutVideo: Runnable = Runnable {
        binding.lnExam.rVideo.visibility = View.VISIBLE
        binding.lnExam.progressBarVideo.visibility = View.GONE
    }

    private fun initShowItemSound() {
        binding.lnExam.lnText.visibility = View.GONE
        binding.lnExam.lnVideo.visibility = View.GONE
        binding.lnExam.lnSound.visibility = View.VISIBLE
        binding.lnExam.lnImage.visibility = View.GONE
    }

    private fun initAnswersAdapter() {
        binding.lnExam.rcAnswers.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.lnExam.rcAnswers.setHasFixedSize(true)
        binding.lnExam.rcAnswers.adapter = answerAdapter
        answerAdapter.setOnItemClickListener(object : OnClickListenerAny {
            override fun onClickListener(any: Any) = initItemAnswerClickListener(any)
        })
    }

    private fun initItemAnswerClickListener(any: Any) {
        val answer: Answer = any as Answer
        answerAdapter.updateItemList(answer)
        userAnswers.add(answer.id)
    }

    private fun initBundle() {
        categoryId = requireArguments().getInt(CATEGORY_ID_BUNDLE, -1)
        totalQuestion = requireArguments().getInt(TOTAL_QUESTION_BUNDLE, -1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_FULL_SCREEN) initResultIntent(
                data
        )
    }

    private fun initResultIntent(data: Intent?) {
        val currentPosition = data!!.extras!!.getInt(BUNDLE_CURRENT_POSITION, 0)
        when (binding.lnExam.lnVideo.visibility) {
            View.VISIBLE -> initResumeVideo(currentPosition)
        }
    }

    private fun initResumeVideo(currentPosition: Int) {
        binding.lnExam.video.seekTo(currentPosition)
        binding.lnExam.video.start()
        initShowPlayView()
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

    private fun initOnBackPressed() = when {
        questionSort > 1 -> initPreviewQuestionId()
        else -> initDialog()
    }

    private fun initDialog() {
        dialogManager.initExitApp(resources.getString(R.string.do_you_exit),object :OnClickListener{
            override fun onClickListener() {
                if (::timer.isInitialized)
                    timer.cancel()
                navController.popBackStack()
            }
        })
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_CODE_RECORD_AUDIO -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    togglePlayBack()
                } else {
                    toastTools.toast(resources.getString(R.string.no_permission))
                }
                return
            }
        }
    }

    private fun togglePlayBack() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            initShowPauseSound()
        } else {
            initShowPlaySound()
            mediaPlayer.start()
        }
    }

}