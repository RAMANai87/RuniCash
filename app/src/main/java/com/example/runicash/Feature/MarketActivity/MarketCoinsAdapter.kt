package com.example.runicash.Feature.MarketActivity

import com.example.runicash.Feature.CoinActivity.BASE_URL_IMAGE
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runicash.R
import com.example.runicash.apiManager.model.TopCoins
import com.example.runicash.databinding.ItemRecyclerCoinListBinding

class MarketCoinsAdapter(
    private val data: ArrayList<TopCoins.Data>,
    private val recyclerCallBack: RecyclerCallBack
) :
    RecyclerView.Adapter<MarketCoinsAdapter.MarketViewHolder>() {
    private lateinit var binding: ItemRecyclerCoinListBinding

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindCoins(dataCoin: TopCoins.Data) {

            binding.txtPrice.text = dataCoin.dISPLAY.uSD.pRICE
            binding.txtCoinName.text = dataCoin.coinInfo.fullName

            val change = dataCoin.rAW.uSD.cHANGE24HOUR
            if (change > 0) {

                    binding.txtCoinBase.text = change.toString().substring(0, 5) + "%"
                    binding.txtCoinBase.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorGain
                        )
                    )

                } else if (change < 0) {

                    binding.txtCoinBase.text = change.toString().substring(0, 5) + "%"
                    binding.txtCoinBase.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorLoss
                        )
                    )

                }

            val marketCap = dataCoin.rAW.uSD.mKTCAP / 1000000000
            if (marketCap > 0) {

                val indexDot = marketCap.toString().indexOf(".")
                binding.txtMarketCap.text = marketCap.toString().substring(0, indexDot + 3) + " B"

            }

            // load image
            Glide.with(itemView)
                .load(BASE_URL_IMAGE + dataCoin.coinInfo.imageUrl)
                .into(binding.imgItemRecyclerMain)

            binding.root.setOnClickListener {
                recyclerCallBack.onItemClicked(dataCoin)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        binding =
            ItemRecyclerCoinListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding.root)

    }
    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {

        holder.bindCoins(data[position])

    }
    override fun getItemCount(): Int {
        return data.size
    }

    // tell to activity main to clicked on a coin and send it =>
    interface RecyclerCallBack {
        fun onItemClicked(dataCoin: TopCoins.Data)
    }

}