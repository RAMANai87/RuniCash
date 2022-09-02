package com.example.runicash.Feature.CoinActivity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutCoinItem(
    var coinWeb: String? = "sorry,There is no data",
    var coinGithub: String? = "sorry,There is no data" ,
    var coinTwitter: String? = "sorry,There is no data" ,
    var coinReddit: String? = "sorry,There is no data"
) :Parcelable
