package triocalavera.freenomo.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
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

    @SuppressLint("ResourceType")
    override fun onStart() {
        super.onStart()
         val auth: FirebaseAuth = FirebaseAuth.getInstance()
         if (auth.currentUser==null) {
             findNavController().navigate(R.id.login)
             fragmentManager?.beginTransaction()?.remove(this)?.commit()
         }


    }

}