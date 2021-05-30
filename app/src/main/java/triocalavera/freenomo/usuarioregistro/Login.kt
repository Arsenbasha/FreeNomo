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
import triocalavera.freenomo.databinding.LoginFragmentBinding

class Login : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible =
            false
        binding = LoginFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(binding)
        binding.btnInicioSesion.setOnClickListener {
            viewModel.checkFields()
        }
        binding.textViewRegistrar.setOnClickListener {
            viewModel.toRegistro()
        }
        binding.btnCancelar.setOnClickListener {
            viewModel.toHome()
        }

        binding.btnFlechaHome.setOnClickListener {
           viewModel.toHome()
        }
    }
}