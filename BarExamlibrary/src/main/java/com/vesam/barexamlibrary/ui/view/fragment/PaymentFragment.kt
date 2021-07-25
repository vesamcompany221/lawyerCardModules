package com.vesam.barexamlibrary.ui.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.databinding.FragmentPaymentBinding
import com.vesam.barexamlibrary.ui.view.adapter.slider_list.AdapterImageSlider
import com.vesam.barexamlibrary.utils.application.AppQuiz
import com.vesam.barexamlibrary.utils.build_config.BuildConfig.Companion.DELAYED_SLIDER
import org.koin.android.ext.android.inject

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var runnable: Runnable
    private lateinit var navController: NavController
    private val adapterImageSlider: AdapterImageSlider by inject()
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initAction()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initAction() {
        initNavController()
        initToolbar()
        initViewPagerSlider()
        initOnClick()
    }

    private fun initNavController() {
        navController = Navigation.findNavController(AppQuiz.activity, R.id.my_nav_fragment)
    }

    private fun initOnClick() {
        binding.lnPayment.setOnClickListener { navController.navigate(R.id.startTestFragment) }
    }

    private fun initViewPagerSlider() {
        adapterImageSlider.updateList(getList())
        binding.pagerSlider.adapter = adapterImageSlider
        binding.pagerSlider.currentItem = 0
        initAddBottomDots(binding.lnDots, adapterImageSlider.count, 0)
        binding.pagerSlider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                pos: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(pos: Int) =
                initAddBottomDots(binding.lnDots, adapterImageSlider.count, pos)

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        startAutoSlider(adapterImageSlider.count)
    }

    private fun getList(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        list.add("https://safeharborshelter.com/wp-content/uploads/2018/04/court-advocacy-700x400.jpg")
        list.add("https://assets.entrepreneur.com/content/3x2/2000/20150929184415-law-and-justice-patent.jpeg?width=700&crop=2:1")
        list.add("https://eige.europa.eu/sites/default/files/images/justice_102275273.jpg")
        return list
    }

    private fun startAutoSlider(count: Int) {
        runnable = Runnable {
            var pos: Int = binding.pagerSlider.currentItem
            pos += 1
            if (pos >= count) pos = 0
            binding.pagerSlider.currentItem = pos
            handler.postDelayed(runnable, DELAYED_SLIDER)
        }
        handler.postDelayed(runnable, DELAYED_SLIDER)
    }

    private fun initAddBottomDots(lnDots: LinearLayout, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        lnDots.removeAllViews()
        initLoopDots(lnDots, dots)
        if (dots.isNotEmpty()) dots[current]!!.setImageResource(R.drawable.shape_circle)
    }

    private fun initLoopDots(lnDots: LinearLayout, dots: Array<ImageView?>) {
        for (i in dots.indices) {
            dots[i] = ImageView(AppQuiz.context)
            val widthHeight = 15
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(5, 10, 5, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle_outline)
            lnDots.addView(dots[i])
        }
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