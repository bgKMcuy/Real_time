package id.skripsi.kaem.realtime.model


import com.google.gson.annotations.SerializedName

data class ResponseSensor(
    @SerializedName("C_ID")
    val cID: Int,
    @SerializedName("Distance")
    val distance: Double,
    @SerializedName("EC")
    val eC: Int,
    @SerializedName("HST")
    val hST: Int,
    @SerializedName("Kalium")
    val kalium: Int,
    @SerializedName("Moisture")
    val moisture: Double,
    @SerializedName("Nitrogen")
    val nitrogen: Int,
    @SerializedName("output")
    val output: Double,
    @SerializedName("pH")
    val pH: Double,
    @SerializedName("Phospor")
    val phospor: Int,
    @SerializedName("Rain")
    val rain: Int,
    @SerializedName("Suhu")
    val suhu: Double,
    @SerializedName("WiFiRSSI")
    val wiFiRSSI: Int
)