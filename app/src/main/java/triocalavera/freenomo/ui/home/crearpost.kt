package triocalavera.freenomo.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.CrearpostFragmentBinding
import java.util.*


class crearpost : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: CrearpostViewModel
    private lateinit var storage: StorageReference
    private val GALLERY_INTENT = 1;
    private var uri: Uri? = null
    private lateinit var binding: CrearpostFragmentBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible =true
        val view = inflater.inflate(R.layout.crearpost_fragment, container, false)
        binding = CrearpostFragmentBinding.bind(view)
        storage = FirebaseStorage.getInstance().reference
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val categorias = arrayOf("Programación","Videojuegos")
        val spinner = binding.spinner

        spinner.adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                categorias
            )
        }


        binding.createPostFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_INTENT)





        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data?.data
            val uuid = UUID.randomUUID()
            val file = storage.child(uuid.toString()).child(uri!!.lastPathSegment.toString())
            file.putFile(uri!!).addOnSuccessListener {
                Toast.makeText(context, "Se subio correctamente", Toast.LENGTH_SHORT).show()
                Glide.with(requireView().context).load(uri)
                    .into(binding.createPostFoto)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            view?.let {Snackbar.make( it.rootView,"Debes iniciar Sesion para crear un Post",Snackbar.LENGTH_LONG)}
            findNavController().navigate(R.id.login)
        }
    }
}