package triocalavera.freenomo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import triocalavera.freenomo.Adapter.CategoryAdapter
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.CategoryFragmentBinding

class category : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: CategoryFragmentBinding
    private lateinit var arrayList:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.category_fragment, container, false)
        binding= CategoryFragmentBinding.bind(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel.init(binding)
        viewModel.getFirebaseCategory()



    }

}