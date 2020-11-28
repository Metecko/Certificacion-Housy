package cl.giorgioarlandi.hoyse.data.models.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cl.giorgioarlandi.hoyse.data.models.Mansion
import org.jetbrains.annotations.NotNull

@Entity(tableName = "mansiones")
data class MansionEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_api")
    @NotNull
    val idApi: Long,
    @ColumnInfo(name = "name")
    @NotNull
    val name: String,
    @ColumnInfo(name = "price")
    @NotNull
    val price: Int,
    @ColumnInfo(name = "photo_link")
    @NotNull
    val photoLink: String
) {
    fun toMansion(): Mansion {
        return Mansion(idApi, name, price, photoLink, idMansion = id)
    }
}