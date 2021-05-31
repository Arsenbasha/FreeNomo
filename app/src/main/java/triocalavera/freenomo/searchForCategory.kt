package triocalavera.freenomo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import triocalavera.freenomo.databinding.SearchForCategoryFragmentBinding

class searchForCategory : Fragment(), AdapterView.OnItemClickListener {

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
        binding.nombreCategoria.text = args.categoria
        binding.gridSearchCategory.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val action =
            searchForCategoryDirections.actionSearchForCategoryToPostItem(viewModel.getCategoryPost()[position])
        view?.setOnClickListener { v -> Navigation.findNavController(v).navigate(action) }
    }
}