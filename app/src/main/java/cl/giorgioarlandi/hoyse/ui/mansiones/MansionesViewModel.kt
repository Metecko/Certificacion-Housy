package cl.giorgioarlandi.hoyse.ui.mansiones

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.giorgioarlandi.hoyse.data.models.Mansion
import java.util.ArrayList

class MansionesViewModel : ViewModel() {
    private val _mansiones = MutableLiveData<ArrayList<Mansion>>().apply {
        value = ArrayList()
    }
    val mansiones: MutableLiveData<ArrayList<Mansion>> = _mansiones
}