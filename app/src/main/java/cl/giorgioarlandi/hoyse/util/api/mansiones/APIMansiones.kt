package cl.giorgioarlandi.hoyse.util.api.mansiones

import retrofit2.Response
import retrofit2.http.GET

interface APIMansiones {
    @GET("mansions/")
    suspend fun getMansionesResponse(): Response<MansionesResponse>
}