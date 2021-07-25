package com.vesam.barexamlibrary.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.data.model.Test
import com.vesam.barexamlibrary.databinding.FragmentTestBinding
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import com.vesam.barexamlibrary.ui.view.adapter.test_list.TestAdapter
import com.vesam.barexamlibrary.utils.application.AppQuiz
import org.koin.android.ext.android.inject

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private lateinit var navController: NavController
    private val testAdapter: TestAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(layoutInflater)
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
        initAdapter()
    }

    private fun initNavController() {
        navController = Navigation.findNavController(AppQuiz.activity, R.id.my_nav_fragment)
    }

    private fun initAdapter() {
        binding.rcTest.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rcTest.setHasFixedSize(true)
        binding.rcTest.adapter = testAdapter
        testAdapter.setOnItemClickListener(object : OnClickListenerAny {
            override fun onClickListener(any: Any) = initItemClickListener(any)
        })
    }

    private fun initItemClickListener(any: Any) {
        navController.navigate(R.id.paymentFragment)
    }


    private fun getList(): ArrayList<Test> {
        val testList: ArrayList<Test> = ArrayList(getResultList())
        testList.forEach { it.imageId = R.drawable.img1 }
        return testList
    }

    private fun getResultList(): List<Test> {
        val objectArrayString: String = readJSONFromAsset()
        val gson = Gson()
        return gson.fromJson(objectArrayString, Array<Test>::class.java).asList()
    }

    private fun readJSONFromAsset(): String {
        return requireContext().assets.open("json/item_test.json").bufferedReader()
            .use { it.readText() }
    }

    private fun initToolbar() {
        initAppCompatActivityToolbar()
    }

    private fun initAppCompatActivityToolbar() {
        (AppQuiz.activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (AppQuiz.activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
        binding.toolbar.setNavigationOnClickListener { initFinish() }
    }

    private fun initFinish() {
        navController.popBackStack()
    }
}