package cl.giorgioarlandi.hoyse.data.models.database.daos

import androidx.room.*
import cl.giorgioarlandi.hoyse.data.models.database.entities.DetalleEntity
import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionEntity

@Dao
interface MansionesDAO {
    @Insert
    fun insertMansionEntity(mansionEntity: MansionEntity): Long
    @Update
    fun updateMnsionEntity(mansionEntity: MansionEntity)
    @Query("SELECT * FROM mansiones where id_api == :idApi")
    fun getMansion(idApi: Long): MansionEntity?
    @Query("SELECT * FROM mansiones")
    fun getMansiones(): List<MansionEntity>
    @Query("SELECT * FROM detalles WHERE :idMansion == id_mansion")
    fun getDetalle(idMansion: Long): DetalleEntity
}