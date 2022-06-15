package id.skripsi.kaem.realtime.model


import com.google.gson.annotations.SerializedName

data class Sensor(
    @SerializedName("Distance")
    val distance: Int,
    @SerializedName("EC")
    val eC: Int,
    @SerializedName("Kalium")
    val kalium: Int,
    @SerializedName("Moisture")
    val moisture: Int,
    @SerializedName("Nitrogen")
    val nitrogen: Int,
    @SerializedName("pH")
    val pH: Int,
    @SerializedName("Phospor")
    val phospor: Int,
    @SerializedName("Suhu")
    val suhu: Double
)