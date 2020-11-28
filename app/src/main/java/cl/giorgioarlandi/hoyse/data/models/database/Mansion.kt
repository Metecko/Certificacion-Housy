package cl.giorgioarlandi.hoyse.data.models.database

data class Mansion(
    val id: Long,
    val idApi: Long,
    val name: String,
    val price: Int,
    val photoLink: String) {

    var size: Int? = null
    var renovation: Boolean? = null
    var credit: Boolean? = null
    var description: String? = null
    var cause: String? = null
}