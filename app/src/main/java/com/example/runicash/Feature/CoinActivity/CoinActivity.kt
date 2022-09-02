package com.example.runicash.Feature.CoinActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.runicash.Feature.MarketActivity.SEND_DATA_KEY
import com.example.runicash.R
import com.example.runicash.apiManager.model.TopCoins
import com.example.runicash.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {
    private lateinit var fromIntentData : TopCoins.Data
    private lateinit var binding: ActivityCoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        initStatistics()
        initAbout()
        initChart()
    }

    private fun initChart() {

    }

    private fun initAbout() {

    }

    @SuppressLint("SetTextI18n")
    private fun initStatistics() {

        fromIntentData = intent.getParcelableExtra(SEND_DATA_KEY)!!

        binding.layoutStatistics.tvOpenAmount.text =  fromIntentData.dISPLAY.uSD.oPEN24HOUR
        binding.layoutStatistics.tvTodaysHighAmount.text = fromIntentData.dISPLAY.uSD.hIGH24HOUR
        binding.layoutStatistics.tvTodaysLow.text = fromIntentData.dISPLAY.uSD.lOW24HOUR
        binding.layoutStatistics.tvChangeTodays.text = fromIntentData.dISPLAY.uSD.cHANGE24HOUR
        binding.layoutStatistics.txtAlgorithm.text = fromIntentData.coinInfo.algorithm
        binding.layoutStatistics.txtTotalVolume.text = fromIntentData.dISPLAY.uSD.tOTALVOLUME24H
        binding.layoutStatistics.txtMarketCapStatistic.text = fromIntentData.dISPLAY.uSD.mKTCAP
        binding.layoutStatistics.txtSupply.text = fromIntentData.dISPLAY.uSD.sUPPLY

    }
}