package cl.giorgioarlandi.hoyse.ui.detalle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import cl.giorgioarlandi.hoyse.R
import cl.giorgioarlandi.hoyse.data.models.DetalleResolver
import cl.giorgioarlandi.hoyse.data.models.Mansion
import cl.giorgioarlandi.hoyse.data.models.PostLaunch
import cl.giorgioarlandi.hoyse.ui.mansiones.MansionesActivity
import cl.giorgioarlandi.hoyse.util.MansionesRepository
import cl.giorgioarlandi.hoyse.util.mostrarAlerta
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detalle_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private lateinit var mansionesRepo: MansionesRepository

    companion object {
        fun newInstance() = DetalleFragment()
    }

    private lateinit var viewModel: DetalleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detalle_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mansionesRepo = MansionesRepository(requireContext())
        viewModel = ViewModelProvider(this).get(DetalleViewModel::class.java)
        viewModel.mansion.observe(viewLifecycleOwner, {
            if (it != null) {
                setContent(it)
            }
        })

        getDetalle(viewModel)

        fab_correo.setOnClickListener {
            enviarCorreo()
        }
    }

    private fun getDetalle(viewModel: DetalleViewModel) {
        lifecycleScope.launch(Dispatchers.Main) {
            when(val post = mansionesRepo.getDetalle(MansionesRepository.currentMansion!!)) {
                is PostLaunch.Success<DetalleResolver> -> {
                    viewModel.mansion.value = post.value.mansion
                }
                is PostLaunch.Error -> {
                    post.exception.printStackTrace()
                    mostrarAlerta(
                        R.string.mensaje_sin_internet,
                        R.string.titulo_sin_internet,
                        requireContext(),
                        MansionesActivity::class.java
                    )
                }
            }
        }
    }

    private fun setContent(mansion: Mansion) {
        Picasso.get().load(mansion.photoLink).into(photo_view)

        name.text = mansion.name
        price.text = mansion.price.toString()
        terrain.text = getString(R.string.metros_cuadrados, mansion.size!!)
        if (mansion.renovation!!) {
            renovation.text = getString(R.string.requiere_renovacion)
        } else {
            renovation.text = getString(R.string.no_requiere_renovacion)
        }
        if (mansion.credit!!) {
            credit.text = getString(R.string.permite_credito)
        } else {
            credit.text = getString(R.string.no_permite_credito)
        }
        descripcion.text = mansion.description
        motivo.text = getString(R.string.motivo_formato, mansion.cause)
    }

    private fun enviarCorreo() {
        val mansion = MansionesRepository.currentMansion!!
        val emailIntent = Intent(
            Intent.ACTION_SENDTO
            )
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@housy.cl"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Consulta ${mansion.name} id ${mansion.idApi}")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hola\n\nVi la propiedad ${mansion.name} y me gustaría que me contactaran a este correo o al siguiente número _________")
        startActivity(Intent.createChooser(emailIntent, "Enviar correo.."))
    }

}