package cl.giorgioarlandi.hoyse.ui.detalle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.giorgioarlandi.hoyse.data.models.Mansion
import java.util.ArrayList

class DetalleViewModel : ViewModel() {
    private val _mansion = MutableLiveData<Mansion>().apply {
        value = null
    }
    val mansion: MutableLiveData<Mansion> = _mansion
}