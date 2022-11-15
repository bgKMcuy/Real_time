package id.skripsi.kaem.realtime.model


import com.google.gson.annotations.SerializedName

data class ResponData(
    @SerializedName("EC")
    val eC: Int,
    @SerializedName("Kalium")
    val kalium: Int,
    @SerializedName("Moisture")
    val moisture: Double,
    @SerializedName("Nitrogen")
    val nitrogen: Int,
    @SerializedName("pH")
    val pH: Double,
    @SerializedName("Phospor")
    val phospor: Int
)