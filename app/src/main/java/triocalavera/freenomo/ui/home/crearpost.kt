package triocalavera.freenomo.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import triocalavera.freenomo.R

class crearpost : Fragment() {

    companion object {
        fun newInstance() = crearpost()
    }

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
        // TODO: Use the ViewModel
    }

}