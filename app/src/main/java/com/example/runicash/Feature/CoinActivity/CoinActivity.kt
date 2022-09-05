package com.example.runicash.Feature.CoinActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.runicash.Feature.MarketActivity.BUNDLE_ABOUT_COIN
import com.example.runicash.Feature.MarketActivity.BUNDLE_COIN
import com.example.runicash.Feature.MarketActivity.SEND_BUNDLE_KEY
import com.example.runicash.R
import com.example.runicash.apiManager.ApiManager
import com.example.runicash.apiManager.model.ChartData
import com.example.runicash.apiManager.model.TopCoins
import com.example.runicash.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {
    private lateinit var dataCurrentCoin: TopCoins.Data
    private lateinit var aboutCurrentData: AboutCoinItem
    private lateinit var binding: ActivityCoinBinding
    val apiManager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fromIntent = intent.getBundleExtra(SEND_BUNDLE_KEY)!!

        dataCurrentCoin = fromIntent.getParcelable<TopCoins.Data>(BUNDLE_COIN)!!
        aboutCurrentData = fromIntent.getParcelable<AboutCoinItem>(BUNDLE_ABOUT_COIN)!!
        binding.layoutToolbar.toolBarMain.title = dataCurrentCoin.coinInfo.fullName

        initUi()
    }
    // load all part of this activity
    private fun initUi() {
        initStatistics()
        initAbout()
        initChart()
    }
    // -----------------------------------------------
    @SuppressLint("SetTextI18n")
    private fun initChart() {

        var period: String = HOUR
        requestAndShowChartData(period)

        binding.layoutChart.txtChartPrice.text = dataCurrentCoin.dISPLAY.uSD.pRICE
        binding.layoutChart.txtChartChange1.text = dataCurrentCoin.dISPLAY.uSD.cHANGE24HOUR
        binding.layoutChart.txtChartChange2.text = dataCurrentCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 3) + " %"
        val change24h = dataCurrentCoin.rAW.uSD.cHANGEPCT24HOUR
        if (change24h > 0) {

            binding.layoutChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(this, R.color.colorGain)
            )

            binding.layoutChart.txtChartUpDown.text = "▲"
            binding.layoutChart.txtChartUpDown.setTextColor(ContextCompat.getColor(this, R.color.colorGain))
            binding.layoutChart.sparkChartMain.lineColor = ContextCompat.getColor(this, R.color.colorGain)

        } else if (change24h < 0) {
            binding.layoutChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(this, R.color.colorLoss)
            )

            binding.layoutChart.txtChartUpDown.text = "▼"
            binding.layoutChart.txtChartUpDown.setTextColor(ContextCompat.getColor(this, R.color.colorLoss))
            binding.layoutChart.sparkChartMain.lineColor = ContextCompat.getColor(this, R.color.colorLoss)

        }

        binding.layoutChart.radioGroup.setOnCheckedChangeListener { _, radioButton ->

            when (radioButton) {

                R.id.item_chart_12h -> {
                    period = HOUR
                }
                R.id.item_chart_1day -> {
                    period = HOURS24
                }
                R.id.item_chart_1week -> {
                    period = WEEK
                }
                R.id.item_chart_1month -> {
                    period = MONTH
                }
                R.id.item_chart_3month -> {
                    period = MONTH3
                }
                R.id.item_chart_1year -> {
                    period = YEAR
                }
                R.id.item_chart_all -> {
                    period = ALL
                }

            }

            requestAndShowChartData(period)

        }
        binding.layoutChart.sparkChartMain.setScrubListener {

            if (it == null) {
                binding.layoutChart.txtChartPrice.text = dataCurrentCoin.dISPLAY.uSD.pRICE
            } else {
                binding.layoutChart.txtChartPrice.text = "$ " + (it as ChartData.Data).close.toString()
            }

        }

    }
    private fun requestAndShowChartData(period: String) {

        apiManager.getChartData(
            period,
            dataCurrentCoin.coinInfo.name,
            object : ApiManager.ApiCallback<Pair<List<ChartData.Data>, ChartData.Data?>> {
                override fun onSuccess(data: Pair<List<ChartData.Data>, ChartData.Data?>) {

                    val chartAdapter = ChartAdapter(data.first, data.second!!.open.toString())
                    binding.layoutChart.sparkChartMain.adapter = chartAdapter

                }

                override fun onError(errorMessage: String) {
                    if (!NetworkChecker(this@CoinActivity).isInternetConnected){
                        binding.layoutChart.errorOnShowChart.isVisible = true
                        binding.layoutChart.sparkChartMain.isVisible = false
                    }

                }

            })

    }
    // -----------------------------------------------
    private fun initAbout() {

        binding.layoutAbout.linkWebsite.text = aboutCurrentData.coinWeb
        binding.layoutAbout.linkGithub.text = aboutCurrentData.coinGithub
        binding.layoutAbout.linkReddit.text = aboutCurrentData.coinReddit
        binding.layoutAbout.linkTwitter.text = aboutCurrentData.coinTwitter

        binding.layoutAbout.linkReddit.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(aboutCurrentData.coinReddit))
            startActivity(intent)

        }

        binding.layoutAbout.linkWebsite.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(aboutCurrentData.coinWeb))
            startActivity(intent)

        }

        binding.layoutAbout.linkGithub.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(aboutCurrentData.coinGithub))
            startActivity(intent)

        }

    }
    // -----------------------------------------------
    @SuppressLint("SetTextI18n")
    private fun initStatistics() {

        binding.layoutStatistics.tvOpenAmount.text = dataCurrentCoin.dISPLAY.uSD.oPEN24HOUR
        binding.layoutStatistics.tvTodaysHighAmount.text = dataCurrentCoin.dISPLAY.uSD.hIGH24HOUR
        binding.layoutStatistics.tvTodaysLow.text = dataCurrentCoin.dISPLAY.uSD.lOW24HOUR
        binding.layoutStatistics.tvChangeTodays.text = dataCurrentCoin.dISPLAY.uSD.cHANGE24HOUR
        binding.layoutStatistics.txtAlgorithm.text = dataCurrentCoin.coinInfo.algorithm
        binding.layoutStatistics.txtTotalVolume.text = dataCurrentCoin.dISPLAY.uSD.tOTALVOLUME24H
        binding.layoutStatistics.txtMarketCapStatistic.text = dataCurrentCoin.dISPLAY.uSD.mKTCAP
        binding.layoutStatistics.txtSupply.text = dataCurrentCoin.dISPLAY.uSD.sUPPLY

    }
}