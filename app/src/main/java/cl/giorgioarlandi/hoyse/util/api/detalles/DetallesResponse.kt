package cl.giorgioarlandi.hoyse.util.api.detalles

import com.google.gson.annotations.SerializedName

data class DetallesResponse(
    @SerializedName("id") val id : Long,
    @SerializedName("name") val name : String,
    @SerializedName("price") val price : Int,
    @SerializedName("photo") val photo : String,
    @SerializedName("size") val size : Int,
    @SerializedName("renovation") val renovation : Boolean,
    @SerializedName("credit") val credit : Boolean,
    @SerializedName("description") val description : String,
    @SerializedName("cause") val cause : String
)