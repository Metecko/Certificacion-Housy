package cl.giorgioarlandi.hoyse.util.api.detalles

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIDetalle {
    @GET("details/{id_api}")
    suspend fun getDetallesResponse(@Path(value = "id_api", encoded = true) idApi: String): Response<DetallesResponse>
}