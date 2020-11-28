package cl.giorgioarlandi.hoyse.util.api.mansiones

import com.google.gson.annotations.SerializedName

data class MansionesResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("price") val price : Int,
    @SerializedName("photo") val photo : String
)