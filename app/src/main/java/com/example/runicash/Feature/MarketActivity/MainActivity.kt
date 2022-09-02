package com.example.runicash.Feature.MarketActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runicash.Feature.CoinActivity.AboutCoinItem
import com.example.runicash.Feature.CoinActivity.CoinActivity
import com.example.runicash.apiManager.ApiManager
import com.example.runicash.apiManager.model.AboutDataCoin
import com.example.runicash.apiManager.model.TopCoins
import com.example.runicash.databinding.ActivityMainBinding
import com.google.gson.Gson

const val BUNDLE_COIN = "bundle1"
const val BUNDLE_ABOUT_COIN = "bundle2"
const val SEND_BUNDLE_KEY = "bundle"

class MainActivity : AppCompatActivity(), MarketCoinsAdapter.RecyclerCallBack {
    private lateinit var myAdapter: MarketCoinsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var aboutCoinItemMap: MutableMap<String, AboutCoinItem>
    lateinit var dataNews: ArrayList<Pair<String, String>>
    private val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.layoutCoinList.btnShowMoreCoinList.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }
        binding.refreshLayout.setOnRefreshListener {

            initUi()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refreshLayout.isRefreshing = false
            }, 1500)

        }

        initUi()
        getAboutCoinData()

    }

    private fun initUi() {
        getTopNews()
        getTopCoins()
    }

    // this is function to get all news from api =>
    private fun getTopNews() {

        apiManager.getTopNews(object : ApiManager.ApiCallback<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {

                dataNews = data
                refreshNews()
            }

            override fun onError(errorMessage: String) {
                Toast.makeText(this@MainActivity, "Please Check Your Connection !!", Toast.LENGTH_LONG).show()
            }

        })

    }
    private fun refreshNews() {

        val randomNews = (0..49).random()
        binding.layoutNews.txtNews.text = dataNews[randomNews].first
        binding.layoutNews.imgNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[randomNews].second))
            startActivity(intent)
        }

        binding.layoutNews.txtNews.setOnClickListener {
            refreshNews()
        }

    }

    // this function used to get top coin =>
    private fun getTopCoins() {

        apiManager.getTopCoins(object : ApiManager.ApiCallback<List<TopCoins.Data>> {
            override fun onSuccess(data: List<TopCoins.Data>) {

                showDataInRecyclerView(data)

            }

            override fun onError(errorMessage: String) {

            }


        })

    }
    private fun showDataInRecyclerView(data: List<TopCoins.Data>) {

        myAdapter = MarketCoinsAdapter(ArrayList(data), this)
        binding.layoutCoinList.recyclerCoinList.adapter = myAdapter
        binding.layoutCoinList.recyclerCoinList.layoutManager = LinearLayoutManager(this)

    }
    override fun onItemClicked(dataCoin: TopCoins.Data) {

        val intent = Intent(this, CoinActivity::class.java)

        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_COIN, dataCoin)
        bundle.putParcelable(BUNDLE_ABOUT_COIN, aboutCoinItemMap[dataCoin.coinInfo!!.name])

        intent.putExtra(SEND_BUNDLE_KEY, bundle)
        startActivity(intent)

    }

    // get data to use in about part =>
    private fun getAboutCoinData() {

        val currencyInfoToString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        aboutCoinItemMap = mutableMapOf<String, AboutCoinItem>()
        val gson = Gson()
        val aboutCoinToSend = gson.fromJson(currencyInfoToString, AboutDataCoin::class.java)

        aboutCoinToSend.forEach {
            aboutCoinItemMap[it.currencyName!!] = AboutCoinItem(
                it.info.web, it.info.github, it.info.twt, it.info.reddit
            )
        }


    }

}