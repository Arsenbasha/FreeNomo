package triocalavera.freenomo.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import triocalavera.freenomo.R

class DatosUsuario : Fragment() {

    companion object {
        fun newInstance() = DatosUsuario()
    }

    private lateinit var viewModel: DatosUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.datos_usuario_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DatosUsuarioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}