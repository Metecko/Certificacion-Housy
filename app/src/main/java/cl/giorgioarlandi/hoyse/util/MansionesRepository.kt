package cl.giorgioarlandi.hoyse.util

import android.content.Context
import cl.giorgioarlandi.hoyse.data.RoomApplication
import cl.giorgioarlandi.hoyse.data.models.DetalleResolver
import cl.giorgioarlandi.hoyse.data.models.Mansion
import cl.giorgioarlandi.hoyse.data.models.PostLaunch
import cl.giorgioarlandi.hoyse.data.models.MansionesResolver
import cl.giorgioarlandi.hoyse.data.models.database.Database
import cl.giorgioarlandi.hoyse.data.models.database.daos.MansionesDAO
import cl.giorgioarlandi.hoyse.util.api.APIClient
import cl.giorgioarlandi.hoyse.util.api.detalles.APIDetalle
import cl.giorgioarlandi.hoyse.util.api.mansiones.APIMansiones
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MansionesRepository(private val context: Context) {
    private val TAG = this.javaClass.simpleName
    private val database: Database = RoomApplication.database!!
    private val mansionesDAO: MansionesDAO = database.getMansionesDAO()

    companion object {
        var currentMansion: Mansion? = null
    }

    suspend fun getMansiones(): PostLaunch<MansionesResolver> {
        return withContext(Dispatchers.IO) {
            if (hayInternet()) {
                apiMansiones()
            } else {
                localMansiones()
            }
        }
    }

    private suspend fun apiMansiones(): PostLaunch<MansionesResolver> {
        val apiclient = APIClient.client.create(APIMansiones::class.java)
        return try {
            val response = apiclient.getMansionesResponse()
            if (response.isSuccessful) {
                var mansiones = ArrayList<Mansion>()
                for (mansionResponse in response.body()!!) {
                    val mansion = Mansion(
                        mansionResponse.id,
                        mansionResponse.name,
                        mansionResponse.price,
                        mansionResponse.photo.replace("http", "https")
                    )
                    mansiones.add(mansion)
                }
                mansiones = insertMansiones(mansiones)
                PostLaunch.Success(MansionesResolver(mansiones))
            } else {
                PostLaunch.Error(Exception("Error del servidor"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            PostLaunch.Error(Exception("No se pudo abrir RetrofitClient"))
        }
    }

    private fun localMansiones(): PostLaunch<MansionesResolver> {
        val mansionesEntity = mansionesDAO.getMansiones()
        val mansiones = ArrayList<Mansion>()
        mansionesEntity.forEach {
            mansiones.add(it.toMansion())
        }

        return PostLaunch.Success(MansionesResolver(mansiones))
    }

    private fun insertMansiones(mansiones: ArrayList<Mansion>): ArrayList<Mansion> {
        val mansionesConIdMansion = ArrayList<Mansion>()
        for (mansion in mansiones) {
            val mansionEntity = mansionesDAO.getMansion(mansion.idApi)
            if (mansionEntity == null) {
                val idMansion = mansionesDAO.insertMansionEntity(mansion.toMansionEntity())
                val mansionADevolver = mansion.copy(idMansion = idMansion)
                mansionADevolver.idMansion = idMansion
                mansionesConIdMansion.add(mansionADevolver)
            } else {
                mansion.idMansion = mansionEntity.id
                mansionesDAO.updateMnsionEntity(mansion.toMansionEntity())
                mansionesConIdMansion.add(mansion)
            }
        }

        return mansionesConIdMansion
    }

    suspend fun getDetalle(mansion: Mansion): PostLaunch<DetalleResolver> {
        return withContext(Dispatchers.IO) {
            if (hayInternet()) {
                apiDetalle(mansion)
            } else {
                localDetalle(mansion)
            }
        }
    }

    private suspend fun apiDetalle(mansion: Mansion): PostLaunch<DetalleResolver> {
        val apiclient = APIClient.client.create(APIDetalle::class.java)
        return try {
            val response = apiclient.getDetallesResponse(mansion.idApi.toString())
            if (response.isSuccessful) {
                val body = response.body()!!
                mansion.size = body.size
                mansion.renovation = body.renovation
                mansion.credit = body.credit
                mansion.description = body.description
                mansion.cause = body.cause

                val mansionADevolver = insertDetalle(mansion)
                PostLaunch.Success(DetalleResolver(mansionADevolver))
            } else {
                PostLaunch.Error(Exception("Error del servidor"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            PostLaunch.Error(Exception("No se pudo abrir RetrofitClient"))
        }
    }

    private fun localDetalle(mansion: Mansion): PostLaunch<DetalleResolver> {
        return if (mansion.description != null) {
            PostLaunch.Success(DetalleResolver(mansion))
        } else {
            PostLaunch.Error(Exception("No existe la mansion localmente"))
        }
    }

    private fun insertDetalle(mansion: Mansion): Mansion {
        val detalleEntity = mansionesDAO.getDetalle(mansion.idMansion)
        return if (detalleEntity == null) {
            val idDetalle = mansionesDAO.insertDetalleEntity(mansion.toDetalleEntity())
            mansion.copy(idDetalle = idDetalle)
        } else {
            mansion.idDetalle = detalleEntity.id
            mansionesDAO.updateDetalleEntity(mansion.toDetalleEntity())
            mansion
        }
    }
}