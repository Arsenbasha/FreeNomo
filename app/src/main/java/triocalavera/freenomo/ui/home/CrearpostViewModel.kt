package triocalavera.freenomo.ui.home

import android.R
import android.app.Application
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import triocalavera.freenomo.databinding.CrearpostFragmentBinding


class CrearpostViewModel(application: Application) : AndroidViewModel(application){


    private lateinit var categorias: ArrayList<String>
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var _binding: CrearpostFragmentBinding

    fun init(
        bindig: CrearpostFragmentBinding
    ) {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        _binding = bindig
        categorias = ArrayList()

    }

    fun getFirebaseCategory(){
        categorias = ArrayList()
        val database = Firebase.database.reference
        Log.d("Categorias", "En categorias")
        database.child("categorias").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    categorias.add(snapshot.getValue(String::class.java)!!.capitalize())
                }
                setCategorias()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Categorias", "Error : loadPost:onCancelled $error")
            }
        })

    }
    private fun setCategorias(){
        val spinner = _binding.spinner
        spinner.adapter = _binding.root.context?.let {
            ArrayAdapter(
                it,
                R.layout.simple_spinner_dropdown_item,
                categorias
            )
        }

    }




}