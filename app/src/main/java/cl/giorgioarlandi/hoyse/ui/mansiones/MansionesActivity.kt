package cl.giorgioarlandi.hoyse.ui.mansiones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.giorgioarlandi.hoyse.R

class MansionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    MansionesFragment.newInstance()
                )
                .commitNow()
        }
    }
}