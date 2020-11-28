package cl.giorgioarlandi.hoyse.ui.mansiones

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.giorgioarlandi.hoyse.R
import cl.giorgioarlandi.hoyse.data.adapters.MansionesAdapter
import cl.giorgioarlandi.hoyse.data.models.Mansion
import cl.giorgioarlandi.hoyse.data.models.PostLaunch
import cl.giorgioarlandi.hoyse.data.models.MansionesResolver
import cl.giorgioarlandi.hoyse.util.MansionesRepository
import kotlinx.android.synthetic.main.mansiones_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MansionesFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private lateinit var mansionesRepo: MansionesRepository

    companion object {
        fun newInstance() =
            MansionesFragment()
    }

    private lateinit var viewModel: MansionesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.mansiones_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mansionesRepo = MansionesRepository(requireContext())
        viewModel = ViewModelProvider(this).get(MansionesViewModel::class.java)
        viewModel.mansiones.observe(viewLifecycleOwner, {
            val adapter = MansionesAdapter(it, requireContext())
            mansiones_rv.adapter = adapter
            mansiones_rv.layoutManager = LinearLayoutManager(requireContext())
        })

        getMansiones(viewModel)
    }


    private fun getMansiones(viewModel: MansionesViewModel) {
        mostrarCargando(cargando = true, vacio = false)
        var mansiones = ArrayList<Mansion>()
        lifecycleScope.launch(Dispatchers.Main) {
            when(val resolver = mansionesRepo.getMansiones()) {
                is PostLaunch.Success<MansionesResolver> -> {
                    viewModel.mansiones.value = resolver.value.mansiones
                    mansiones = resolver.value.mansiones
                }
                else -> {
                    Log.e(TAG, "getMansiones: fracaso")
                }
            }

            val vacio = mansiones.size == 0
            mostrarCargando(false, vacio)
        }
    }

    private fun mostrarCargando(cargando: Boolean, vacio: Boolean) {
        if (cargando) {
            progress_mansiones.visibility = View.VISIBLE
            mansiones_rv.visibility = View.GONE
        } else {
            progress_mansiones.visibility = View.GONE
            mansiones_rv.visibility = View.VISIBLE
        }
    }
}