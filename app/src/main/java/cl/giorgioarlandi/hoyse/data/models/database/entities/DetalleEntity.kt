package cl.giorgioarlandi.hoyse.data.models.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import cl.giorgioarlandi.hoyse.data.models.Mansion
import org.jetbrains.annotations.NotNull

@Entity(tableName = "detalles",
    foreignKeys = [
        ForeignKey(entity = MansionEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_mansion"],
            onDelete = ForeignKey.CASCADE)
    ])
data class DetalleEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_mansion")
    @NotNull
    val idMansion: Long,
    @ColumnInfo(name = "size")
    @NotNull
    val size: Int,
    @ColumnInfo(name = "renovation")
    @NotNull
    val renovation: Boolean,
    @ColumnInfo(name = "credit")
    @NotNull
    val credit: Boolean,
    @ColumnInfo(name = "description")
    @NotNull
    val description: String,
    @ColumnInfo(name = "cause")
    @NotNull
    val cause: String
) {
    fun toMansion(mansion: Mansion): Mansion {
        return mansion.copy(size = size, renovation = renovation, credit = credit, description = description, cause = cause)
    }
}