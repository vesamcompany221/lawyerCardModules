package com.vesam.barexamlibrary.ui.view.activity

import android.os.Bundle
import com.vesam.barexamlibrary.databinding.ActivityQuizBinding
import com.vesam.barexamlibrary.utils.base.BaseActivity

class QuizActivity : BaseActivity() {

    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}