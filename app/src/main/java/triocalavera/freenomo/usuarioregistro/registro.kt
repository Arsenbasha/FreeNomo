package triocalavera.freenomo.usuarioregistro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.RegistroFragmentBinding


class registro : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var bindig: RegistroFragmentBinding
    private lateinit var viewModel: RegistroViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.registro_fragment, container, false)
        bindig = RegistroFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistroViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        bindig.registrarBoton.setOnClickListener {
            checkFields()
        }
    }

    private fun checkFields() {

        if (!(bindig.nombreUsuario.editText!!.text.isEmpty() || bindig.correoUsuario.editText!!.text.isEmpty()
                    || bindig.telefonoUsuario.editText!!.text.isEmpty() || bindig.contraUsuario.editText!!.text.isEmpty()
                    || bindig.repetirContraUsuario.editText!!.text.isEmpty())
        ) {
            val contra: String = bindig.contraUsuario.editText!!.text.toString()
            val repeContra: String = bindig.repetirContraUsuario.editText!!.text.toString()
            Toast.makeText(this.context, "Los datos estan llenos ", Toast.LENGTH_SHORT).show()
            if (contra == repeContra) {
                registroUsuario()
            } else {
                Toast.makeText(
                    this.context,
                    "La contraseña es ${contra} y la repe es ${repeContra}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else Toast.makeText(this.context, "Los campos estan vacios", Toast.LENGTH_SHORT).show()
    }

    private fun registroUsuario() {
        auth.createUserWithEmailAndPassword(
            bindig.correoUsuario.editText!!.text.toString(),
            bindig.contraUsuario.editText!!.text.toString()
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("INFO", "Se esta creando el usuario")
                    val map: MutableMap<String, Any> = HashMap()
                    map["nombreCompleto"] = bindig.nombreUsuario.editText!!.text.toString()
                    map["correo"] = bindig.correoUsuario.editText!!.text.toString()
                    map["telefono"] = bindig.telefonoUsuario.editText!!.text.toString()
                    val id = this.auth.currentUser!!.uid

                    database.child("Users").child(id).setValue(map)
                        .addOnSuccessListener {
                            Log.d("INFO", "el usuario se ha creado correctamente ")
                            Toast.makeText(
                                context,
                                "Usuario creado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.nav_home)
                        }

                }
            }

    }


}