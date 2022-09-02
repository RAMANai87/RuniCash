package com.example.runicash.Feature.CoinActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.runicash.Feature.MarketActivity.BUNDLE_ABOUT_COIN
import com.example.runicash.Feature.MarketActivity.BUNDLE_COIN
import com.example.runicash.Feature.MarketActivity.SEND_BUNDLE_KEY
import com.example.runicash.Feature.MarketActivity.SEND_DATA_KEY
import com.example.runicash.apiManager.model.TopCoins
import com.example.runicash.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {
    private lateinit var dataCurrentCoin: TopCoins.Data
    private lateinit var aboutCurrentData: AboutCoinItem
    private lateinit var binding: ActivityCoinBinding
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

    private fun initUi() {
        initStatistics()
        initAbout()
        initChart()
    }

    private fun initChart() {

    }

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