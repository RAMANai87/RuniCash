package com.example.runicash.Feature.MarketActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runicash.Feature.CoinActivity.CoinActivity
import com.example.runicash.apiManager.ApiManager
import com.example.runicash.apiManager.model.TopCoins
import com.example.runicash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MarketCoinsAdapter.RecyclerCallBack {
    private lateinit var myAdapter :MarketCoinsAdapter
    private lateinit var binding: ActivityMainBinding
    lateinit var dataNews :ArrayList<Pair<String, String>>
    private val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()

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

    }
    override fun onResume() {
        super.onResume()
        initUi()
    }

    private fun initUi() {
        getTopNews()
        getTopCoins()
    }

    // this is function to get all news from api
    private fun getTopNews() {

        apiManager.getTopNews(object : ApiManager.ApiCallback<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {

                dataNews = data
                refreshNews()
            }

            override fun onError(errorMessage: String) {
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
            }

        })

    }
    private fun refreshNews () {

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

    // this function used to get top coin
    private fun getTopCoins() {

        apiManager.getTopCoins(object :ApiManager.ApiCallback<List<TopCoins.Data>> {
            override fun onSuccess(data: List<TopCoins.Data>) {

                showDataInRecyclerView(data)

            }

            override fun onError(errorMessage: String) {

            }


        } )

    }
    private fun showDataInRecyclerView(data :List<TopCoins.Data>) {

        myAdapter = MarketCoinsAdapter(ArrayList(data), this)
        binding.layoutCoinList.recyclerCoinList.adapter = myAdapter
        binding.layoutCoinList.recyclerCoinList.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemClicked(dataCoin: TopCoins.Data) {

        val intent = Intent(this, CoinActivity::class.java)
        startActivity(intent)

    }

}