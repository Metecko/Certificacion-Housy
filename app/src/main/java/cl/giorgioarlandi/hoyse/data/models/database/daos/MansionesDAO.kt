package cl.giorgioarlandi.hoyse.data.models.database.daos

import androidx.room.Dao
import androidx.room.Query
import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionDetalleEntity
import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionEntity

@Dao
interface MansionesDAO {
    @Query("SELECT * FROM mansiones")
    fun getMansiones(): List<MansionEntity>
    @Query("SELECT * FROM mansiones_detalle WHERE :idMansion == id_mansion")
    fun getMansionDetalle(idMansion: Long): MansionDetalleEntity
}