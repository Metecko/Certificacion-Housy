package cl.giorgioarlandi.hoyse.ui.detalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.giorgioarlandi.hoyse.R

class DetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetalleFragment.newInstance())
                .commitNow()
        }
    }
}