package cl.giorgioarlandi.hoyse.data.models

sealed class PostLaunch<out R> {
    data class Success<out T>(val value: T) : PostLaunch<T>()
    data class Error(val exception: Exception) : PostLaunch<Nothing>()
}