package cl.giorgioarlandi.hoyse.data.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.giorgioarlandi.hoyse.data.models.database.daos.MansionesDAO
import cl.giorgioarlandi.hoyse.data.models.database.entities.DetalleEntity
import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionEntity

@Database(
    entities = [
    MansionEntity::class,
    DetalleEntity::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
   abstract fun getMansionesDAO(): MansionesDAO
}
