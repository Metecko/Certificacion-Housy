package cl.giorgioarlandi.hoyse.ui.mansiones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import cl.giorgioarlandi.hoyse.R

class MansionesFragment : Fragment() {

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
        viewModel = ViewModelProvider(this).get(MansionesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}