package triocalavera.freenomo.usuarioregistro

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.RegistroFragmentBinding

class RegistroViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private lateinit var _auth: FirebaseAuth
    private lateinit var _database: DatabaseReference
    private lateinit var _binding: RegistroFragmentBinding
private lateinit var _fragmentManager: FragmentManager
    fun init(
        bindig: RegistroFragmentBinding,
        fragmentManager: FragmentManager?
    ) {
        _auth = FirebaseAuth.getInstance()
        _database = Firebase.database.reference
        _binding = bindig
        _fragmentManager=fragmentManager!!

    }

    fun checkFields() {

        if (!(_binding.nombreUsuario.editText!!.text.isEmpty() || _binding.correoUsuario.editText!!.text.isEmpty()
                    || _binding.telefonoUsuario.editText!!.text.isEmpty() || _binding.contraUsuario.editText!!.text.isEmpty()
                    || _binding.repetirContraUsuario.editText!!.text.isEmpty())
        ) {
            val contra: String = _binding.contraUsuario.editText!!.text.toString()
            val repeContra: String = _binding.repetirContraUsuario.editText!!.text.toString()
            Toast.makeText(_binding.root.context, "Los datos estan llenos ", Toast.LENGTH_SHORT)
                .show()
            if (contra == repeContra) {

                registroUsuario()
            } else {
                Toast.makeText(
                    _binding.root.context,
                    "La contraseña es ${contra} y la repe es ${repeContra}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else Toast.makeText(_binding.root.context, "Los campos estan vacios", Toast.LENGTH_SHORT)
            .show()
    }

    private fun registroUsuario() {
        _auth.createUserWithEmailAndPassword(
            _binding.correoUsuario.editText!!.text.toString(),
            _binding.contraUsuario.editText!!.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _binding.progresBarRegistro.visibility = View.VISIBLE
                _binding.linearLayoutRegistro.visibility= View.GONE
                    Log.d("INFO", "Se esta creando el usuario")
                val map: MutableMap<String, Any> = HashMap()
                map["nombreCompleto"] = _binding.nombreUsuario.editText!!.text.toString()
                map["correo"] = _binding.correoUsuario.editText!!.text.toString()
                map["telefono"] = _binding.telefonoUsuario.editText!!.text.toString()
                val id = _auth.currentUser!!.uid
                _database.child("Users").child(id).setValue(map)
                    .addOnSuccessListener {
                        Log.d("INFO", "el usuario se ha creado correctamente ")
                        Toast.makeText(
                            _binding.root.context,
                            "Usuario creado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        _binding.progresBarRegistro.visibility = View.GONE
                        _binding.root.findNavController().navigate(R.id.nav_home)
                    }
            }
        }
    }

    fun toInicioSesion() = _binding.root.findNavController().navigate(R.id.login)
    fun toHome() = _binding.root.findNavController().navigate(R.id.nav_home)
}