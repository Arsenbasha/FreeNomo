package triocalavera.freenomo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import triocalavera.freenomo.Adapter.PostAdapter
import triocalavera.freenomo.Model.Post
import triocalavera.freenomo.R

class HomeFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var homeViewModel: HomeViewModel
private  lateinit var  arrayList:ArrayList<Post>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible =
            true
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val grid = view.findViewById<GridView>(R.id.grid)
         arrayList = arrayList()
        val postAdapter =PostAdapter(requireContext(),arrayList)
        grid.adapter=postAdapter
        grid.onItemClickListener=this

    }

    private fun arrayList(): ArrayList<Post> {
        var arrayList: ArrayList<Post> = ArrayList()
        arrayList.add(
            Post(
                "post 1", "infromatica",
                "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/2400/public/media/image/2019/07/origen-nombres-informatica-nunca-hubieras-imaginado_2.jpg",
                "test "
            )
        )
        arrayList.add(
            Post(
                "post 2", "electricista",
                "https://www.lecciona.com/wp-content/uploads/2020/11/ELECTRICISTA.jpg",
                "test  electicista"
            )
        )
        arrayList.add(
            Post(
                "post 3", "infromatica 2",
                "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/2400/public/media/image/2019/07/origen-nombres-informatica-nunca-hubieras-imaginado_2.jpg",
                "test "
            )
        )
        arrayList.add(
            Post(
                "post 4", "electricista2",
                "https://www.lecciona.com/wp-content/uploads/2020/11/ELECTRICISTA.jpg",
                "test  electicista"
            )
        )
        arrayList.add(
            Post(
                "post 1", "infromatica",
                "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/2400/public/media/image/2019/07/origen-nombres-informatica-nunca-hubieras-imaginado_2.jpg",
                "test "
            )
        )
        arrayList.add(
            Post(
                "post 2", "electricista",
                "https://www.lecciona.com/wp-content/uploads/2020/11/ELECTRICISTA.jpg",
                "test  electicista"
            )
        )
        arrayList.add(
            Post(
                "post 3", "infromatica 2",
                "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/2400/public/media/image/2019/07/origen-nombres-informatica-nunca-hubieras-imaginado_2.jpg",
                "test "
            )
        )
        arrayList.add(
            Post(
                "post 4", "electricista2",
                "https://www.lecciona.com/wp-content/uploads/2020/11/ELECTRICISTA.jpg",
                "test  electicista"
            )
        )
        return arrayList

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var post = arrayList.get(position)
        Toast.makeText(context, "$post" , Toast.LENGTH_LONG).show()
    }
}