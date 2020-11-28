package cl.giorgioarlandi.hoyse.util

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import cl.giorgioarlandi.hoyse.R
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

fun mostrarAlerta(
    mensaje: Int,
    titulo: Int,
    context: Context,
    actividadObjetivo: Class<*>? = null
) {
    AlertDialog.Builder(context).apply {
        setTitle(titulo)
        setMessage(mensaje)
        setPositiveButton(R.string.entendido, DialogInterface.OnClickListener { dialog, id ->
            if (actividadObjetivo != null) {
                val intent = Intent(context, actividadObjetivo)
                context.startActivity(intent)
            }
        })
        show()
    }
}