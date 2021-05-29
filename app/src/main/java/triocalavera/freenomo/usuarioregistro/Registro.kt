package triocalavera.freenomo.usuarioregistro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.RegistroFragmentBinding


class Registro : Fragment() {
    private lateinit var binding: RegistroFragmentBinding
    private lateinit var viewModel: RegistroViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.registro_fragment, container, false)
        binding = RegistroFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(RegistroViewModel::class.java)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible =
            false
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(binding)
        binding.registrarBoton.setOnClickListener {
            viewModel.checkFields()
        }
        binding.textViewInicioSesion.setOnClickListener {
        viewModel.toInicioSesion()
        }
    }


}