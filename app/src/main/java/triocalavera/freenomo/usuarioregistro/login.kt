package triocalavera.freenomo.usuarioregistro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.LoginFragmentBinding

class login : Fragment() {


    companion object {
        fun newInstance() = login()
    }

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.login_fragment, container, false)
        binding = LoginFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewRegistrar.setOnClickListener {
            Log.d("Test", "click")
            Toast.makeText(this.context, "click", Toast.LENGTH_SHORT).show()
        }


        binding.btnInicioSesion.setOnClickListener {

            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible=true
            findNavController().navigate(R.id.nav_home)
        }
       requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible=false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


    }

}