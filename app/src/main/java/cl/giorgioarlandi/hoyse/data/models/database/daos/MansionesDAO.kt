package cl.giorgioarlandi.hoyse.data.models.database.daos

import androidx.room.Dao
import androidx.room.Query
import cl.giorgioarlandi.hoyse.data.models.database.entities.DetalleEntity
import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionEntity

@Dao
interface MansionesDAO {
    @Query("SELECT * FROM mansiones")
    fun getMansiones(): List<MansionEntity>
    @Query("SELECT * FROM detalles WHERE :idMansion == id_mansion")
    fun getDetalle(idMansion: Long): DetalleEntity
}