package triocalavera.freenomo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import triocalavera.freenomo.databinding.SearchForCategoryFragmentBinding

class searchForCategory : Fragment() {
    private lateinit var searchForCategoryViewModel: SearchForCategoryViewModel
    private lateinit var database: DatabaseReference
    private lateinit var viewModel: SearchForCategoryViewModel
    private lateinit var binding: SearchForCategoryFragmentBinding

    val args: searchForCategoryArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SearchForCategoryViewModel::class.java)
        val view = inflater.inflate(R.layout.search_for_category_fragment, container, false)
        binding = SearchForCategoryFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchForCategoryViewModel::class.java)
        viewModel.init(binding, requireActivity(), args.categoria)
        viewModel.obtenerPost()
        binding.nombreCategoria.text=args.categoria


    }

}