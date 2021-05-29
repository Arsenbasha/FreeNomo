package triocalavera.freenomo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import triocalavera.freenomo.R


class crearpost : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: CrearpostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.crearpost_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CrearpostViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            view?.let {
                val snackbar= Snackbar.make(it.rootView, "Debes iniciar Sesion para crear un Post", Snackbar.LENGTH_LONG)
            }
            findNavController().navigate(R.id.login)
        }
    }

}