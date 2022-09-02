package com.example.runicash.Feature.CoinActivity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutCoinItem(
    var coinWeb: String?,
    var coinGithub: String?,
    var coinTwitter: String?,
    var coinReddit: String?
) : Parcelable
