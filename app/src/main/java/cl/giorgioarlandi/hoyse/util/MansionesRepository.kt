package cl.giorgioarlandi.hoyse.util

import android.content.Context
import android.util.Log
import cl.giorgioarlandi.hoyse.data.RoomApplication
import cl.giorgioarlandi.hoyse.data.models.Mansion
import cl.giorgioarlandi.hoyse.data.models.PostLaunch
import cl.giorgioarlandi.hoyse.data.models.MansionesResolver
import cl.giorgioarlandi.hoyse.data.models.database.Database
import cl.giorgioarlandi.hoyse.data.models.database.daos.MansionesDAO
import cl.giorgioarlandi.hoyse.util.api.APIClient
import cl.giorgioarlandi.hoyse.util.api.mansiones.APIMansiones
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MansionesRepository(private val context: Context) {
    private val TAG = this.javaClass.simpleName
    private val database: Database = RoomApplication.database!!
    private val mansionesDAO: MansionesDAO = database.getMansionesDAO()

    suspend fun getMansiones(): PostLaunch<MansionesResolver> {
        return withContext(Dispatchers.IO) {
            if (hayInternet()) {
                Log.e(TAG, "getMansiones: api")
                apiMansiones()
            } else {
                Log.e(TAG, "getMansiones: local")
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
        val mansionesConIdDatabase = ArrayList<Mansion>()
        for (mansion in mansiones) {
            val mansionEntity = mansionesDAO.getMansion(mansion.idApi)
            if (mansionEntity == null) {
                val idDatabase = mansionesDAO.insertMansionEntity(mansion.toMansionEntity())
                val mansionADevolver = mansion.copy()
                mansionADevolver.idDatabase = idDatabase
                mansionesConIdDatabase.add(mansionADevolver)
            } else {
                mansion.idDatabase = mansionEntity.id
                mansionesDAO.updateMnsionEntity(mansion.toMansionEntity())
                mansionesConIdDatabase.add(mansion)
            }
        }

        return mansionesConIdDatabase
    }
}