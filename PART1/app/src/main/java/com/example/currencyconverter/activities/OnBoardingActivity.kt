package com.example.tech_mart_application.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.currencyconverter.adapters.SliderLayout
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.models.SliderInfo
import com.example.currencyconverter.R
import com.example.currencyconverter.utils.PreferenceManager
import com.example.currencyconverter.databinding.ActivityOnBoardingBinding
import com.example.tvshowsapplication.Utils.Constants.Companion.IS_STARTED


class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private val mListSliderInfo = mutableListOf<SliderInfo>()
    private lateinit var mSliderLayout: SliderLayout
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        //Load Slider Info
        loadSliderInfo()


        binding.btnSkip.setOnClickListener {
            onClickButtonSkip()
        }

        binding.btnBack.setOnClickListener {
            onClickButtonBack()
        }

        binding.btnNext.setOnClickListener {
            onClickButtonNext()
        }

        binding.containerPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (getCurrentItem() > 0) {
                    binding.btnBack.visibility = View.VISIBLE
                } else {
                    binding.btnBack.visibility = View.INVISIBLE
                }
            }

        })

    }

    @SuppressLint("SetTextI18n")
    private fun onClickButtonNext() {
        if (getCurrentItem() == 3) {
            preferenceManager.putBoolean(IS_STARTED, true)
            val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            binding.containerPager.currentItem = getCurrentItem() + 1
        }
    }

    private fun onClickButtonBack() {
        if (getCurrentItem() > 0) {
            binding.containerPager.currentItem = getCurrentItem() - 1
        }
    }

    private fun onClickButtonSkip() {
        preferenceManager.putBoolean(IS_STARTED, true)
        val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun loadSliderInfo() {
        binding.btnNext.visibility = View.VISIBLE
        mListSliderInfo.add(SliderInfo("home.svg", R.string.heading_one, R.string.desc_one))
        mListSliderInfo.add(SliderInfo("bookmark.svg", R.string.heading_two, R.string.desc_two))
        mListSliderInfo.add(
            SliderInfo(
                "news.svg",
                R.string.heading_three,
                R.string.desc_three
            )
        )
        mListSliderInfo.add(
            SliderInfo(
                "chart.svg",
                R.string.heading_fourth,
                R.string.desc_fourth
            )
        )
        mListSliderInfo.add(
            SliderInfo(
                "search.svg",
                R.string.heading_fourth,
                R.string.desc_fourth
            )
        )
        mSliderLayout = SliderLayout(this.mListSliderInfo)
        binding.containerPager.apply {
            offscreenPageLimit = 1
            adapter = mSliderLayout
        }

        binding.circleIndicator.setViewPager(binding.containerPager)
        mSliderLayout.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)
    }

    private fun getCurrentItem(): Int {
        return binding.containerPager.currentItem
    }
}