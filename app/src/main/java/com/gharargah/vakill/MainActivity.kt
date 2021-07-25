package com.gharargah.vakill

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vesam.barexamlibrary.utils.option.InitOption

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitOption.start(this, baseUrl, token, userId)

    }
}