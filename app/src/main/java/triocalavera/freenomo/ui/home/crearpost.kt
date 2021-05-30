package triocalavera.freenomo.ui.home

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import triocalavera.freenomo.Model.Post
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.CrearpostFragmentBinding
import java.util.*
import kotlin.collections.ArrayList


class crearpost : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: CrearpostViewModel
    private lateinit var storage: StorageReference
    private val GALLERY_INTENT = 1;
    private var uuid = UUID.randomUUID().toString()
    private var uri: Uri? = null
    private lateinit var binding: CrearpostFragmentBinding
    var categorias = ArrayList<String>()
    private lateinit var categoria: String
    private lateinit var database: DatabaseReference
    private var foto = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible =
            true
        val view = inflater.inflate(R.layout.crearpost_fragment, container, false)
        binding = CrearpostFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(CrearpostViewModel::class.java)
        storage = FirebaseStorage.getInstance().reference
        database = FirebaseDatabase.getInstance().reference
        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(binding)
        viewModel.getFirebaseCategory()

        binding.createPostFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_INTENT)
        }
        binding.fabCrearPost.setOnClickListener {
            checkFields()
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                categoria = binding.spinner.selectedItem.toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Error")
                builder.setMessage("Debe seleccionar una categoria")
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()

            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkFields() {
        val titulo = binding.tituloPost.editText!!.text.toString()
        val descripcion = binding.descriocionPost.editText!!.text.toString()
        val precio = binding.precioPost.editText!!.text.toString()
        val telefon = binding.telefonoPost.editText!!.text.toString()
        if (!(titulo.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || telefon.isEmpty() || uri == null)) {
            val file = storage.child(uuid).child(uri!!.lastPathSegment.toString())
            getSize()
            file.putFile(uri!!)
                .addOnCompleteListener(OnCompleteListener<UploadTask.TaskSnapshot> { task ->
                    if (task.isSuccessful) {

                        task.result!!.storage.downloadUrl.addOnSuccessListener {
                            foto=it.toString()
                            createPost(titulo, descripcion, precio, telefon)
                        }
                    }
                })
        }
    }
    private var i: Long = 0
    private fun getSize() {
        database.child("post").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                i = dataSnapshot.childrenCount+1
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Categorias", "Error : loadPost:onCancelled $error")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun createPost(titulo: String, descripcion: String, precio: String, telefon: String) {

        val map: MutableMap<String, Any> = HashMap()
        map["titulo"] = titulo
        map["descripcion"] = descripcion
        map["precio"] = precio
        map["telefono"] = telefon
        map["uuid"] = uuid
        map["categoria"] = categoria
        map["foto"] = foto
        database.child("post").child(i.toString()).setValue(map).addOnSuccessListener {
            findNavController().navigate(R.id.nav_home)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data?.data
            Glide.with(requireView().context).load(uri)
                .into(binding.createPostFoto)
        }
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            view?.let {
                Snackbar.make(
                    it.rootView,
                    "Debes iniciar Sesion para crear un Post",
                    Snackbar.LENGTH_LONG
                )
            }
            findNavController().navigate(R.id.login)
        }
    }
}