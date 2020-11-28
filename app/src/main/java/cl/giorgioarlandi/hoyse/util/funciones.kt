package cl.giorgioarlandi.hoyse.util

import java.net.InetAddress

fun hayInternet(): Boolean {
    return try {
        val ipAddr: InetAddress = InetAddress.getByName("google.com")
        //You can replace it with your name
        !ipAddr.equals("")
    } catch (e: Exception) {
        false
    }
}