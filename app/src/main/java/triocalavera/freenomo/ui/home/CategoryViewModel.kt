package triocalavera.freenomo.ui.home

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import triocalavera.freenomo.Adapter.CategoryAdapter
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.CategoryFragmentBinding

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var categorias: ArrayList<String>
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var _binding: CategoryFragmentBinding
    fun init(
        bindig: CategoryFragmentBinding
    ) {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        _binding = bindig
        categorias = ArrayList()

    }


    fun getFirebaseCategory() {
        categorias = ArrayList()
        val database = Firebase.database.reference
        Log.d("Categorias", "En categorias")
        database.child("categorias").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    categorias.add(snapshot.getValue(String::class.java)!!)
                 getCategory()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Categorias", "Error : loadPost:onCancelled $error")
            }
        })
    }

    fun getCategory(){
        val grid = _binding.gridCategory
        val categoryAdapter = CategoryAdapter(_binding.root.context, categorias)
        grid.adapter = categoryAdapter
    }
}