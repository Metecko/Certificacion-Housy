package cl.giorgioarlandi.hoyse.data.models

import cl.giorgioarlandi.hoyse.data.models.database.entities.MansionEntity

data class Mansion(
    val idApi: Long,
    val name: String,
    val price: Int,
    val photoLink: String) {

    var idDatabase: Long = 0
    var size: Int? = null
    var renovation: Boolean? = null
    var credit: Boolean? = null
    var description: String? = null
    var cause: String? = null

    fun toMansionEntity(): MansionEntity {
        return MansionEntity(
            idDatabase,
            idApi,
            name,
            price,
            photoLink
        )
    }
}