package cl.giorgioarlandi.hoyse.data.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionDetalleEntity
import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionEntity

@Database(
    entities = [
    MansionEntity::class,
    MansionDetalleEntity::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
   // abstract fun getClienteDao(): ClientesDAO
}
