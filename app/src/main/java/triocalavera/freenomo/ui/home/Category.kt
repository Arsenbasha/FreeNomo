package triocalavera.freenomo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.CategoryFragmentBinding

class Category : Fragment() {
    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: CategoryFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.category_fragment, container, false)
        binding = CategoryFragmentBinding.bind(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel.init(binding)
        viewModel.getFirebaseCategory()

    }
}