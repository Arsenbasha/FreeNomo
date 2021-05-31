package triocalavera.freenomo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import triocalavera.freenomo.Adapter.PostAdapter
import triocalavera.freenomo.Model.Post
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.FragmentHomeBinding
import triocalavera.freenomo.searchForCategoryDirections

class HomeFragment : Fragment(), AdapterView.OnItemClickListener {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var arrayList: ArrayList<Post>
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        binding= FragmentHomeBinding.bind(root)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible =
            true
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.init(binding,requireActivity())
        viewModel.obtenerPost()
        binding.homeBuscar.setOnClickListener {
           viewModel.setSearch()
            viewModel.obtenerPost()
        }
        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.crearpost)
        }
        binding.grid.onItemClickListener=this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val action =  HomeFragmentDirections.actionNavHomeToPostItem(viewModel.getPostList()[position])
        view?.setOnClickListener { v -> Navigation.findNavController(v).navigate(action) }
    }


}