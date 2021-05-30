package triocalavera.freenomo.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.DatosUsuarioFragmentBinding

class DatosUsuario : Fragment() {

    private lateinit var binding: DatosUsuarioFragmentBinding
    private lateinit var viewModel: DatosUsuarioViewModel

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.datos_usuario_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DatosUsuarioViewModel::class.java)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = false
        binding = DatosUsuarioFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(binding)

        binding.cambiarNombreUsuario.setOnClickListener {
            viewModel.alertaCambiarNombre(view, requireActivity())

        }

        binding.cambiarCorreo.setOnClickListener {
            viewModel.alertaCambiarCorreo(view, requireActivity())
        }

        binding.cambiarTelefono.setOnClickListener {
            viewModel.alertaCambiarTelefono(view, requireActivity())
        }

        binding.cambiarContrasena.setOnClickListener {
            viewModel.alertaCambiarContra(view, requireActivity())
        }

        binding.eliminarCuenta.setOnClickListener {
            viewModel.alertaBorrarCuenta(view, requireActivity())
        }



    }





    }

