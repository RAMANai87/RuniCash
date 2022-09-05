package com.example.runicash.apiManager.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ChartData(
    @SerializedName("Aggregated")
    val aggregated: Boolean,
    @SerializedName("ConversionType")
    val conversionType: ConversionType,
    @SerializedName("Data")
    val `data`: List<Data>,
    @SerializedName("FirstValueInArray")
    val firstValueInArray: Boolean,
    @SerializedName("HasWarning")
    val hasWarning: Boolean,
    @SerializedName("RateLimit")
    val rateLimit: RateLimit,
    @SerializedName("Response")
    val response: String,
    @SerializedName("TimeFrom")
    val timeFrom: Int,
    @SerializedName("TimeTo")
    val timeTo: Int,
    @SerializedName("Type")
    val type: Int
) : Parcelable {
    @Parcelize
    data class ConversionType(
        @SerializedName("conversionSymbol")
        val conversionSymbol: String,
        @SerializedName("type")
        val type: String
    ) : Parcelable

    @Parcelize
    data class Data(
        @SerializedName("close")
        val close: Double,
        @SerializedName("conversionSymbol")
        val conversionSymbol: String,
        @SerializedName("conversionType")
        val conversionType: String,
        @SerializedName("high")
        val high: Double,
        @SerializedName("low")
        val low: Double,
        @SerializedName("open")
        val `open`: Double,
        @SerializedName("time")
        val time: Int,
        @SerializedName("volumefrom")
        val volumefrom: Double,
        @SerializedName("volumeto")
        val volumeto: Double
    ) : Parcelable

    @Parcelize
    class RateLimit : Parcelable
}