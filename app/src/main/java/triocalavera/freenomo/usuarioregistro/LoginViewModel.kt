package triocalavera.freenomo.usuarioregistro

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.LoginFragmentBinding

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var _binding: LoginFragmentBinding
    fun toRegistro() = _binding.root.findNavController().navigate(R.id.registro)


    fun init(binding: LoginFragmentBinding) {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        _binding = binding
    }

    fun checkFields() {
        if (!(_binding.campoCorreoElectronico.editText!!.text.isEmpty() ||_binding.campoContra.editText!!.text.isEmpty() )){
            Log.d("INFO", "correo y contraseña llenos! ")
            loginUser( )
        }else if(_binding.campoCorreoElectronico.editText!!.text.isEmpty()){
            Log.d("INFO", " correo en blanco ")
            Toast.makeText(_binding.root.context, "Correo obligatorio", Toast.LENGTH_SHORT).show()
        }else if (_binding.campoContra.editText!!.text.isEmpty()){
            Log.d("INFO", "password en blanco")
            Toast.makeText(_binding.root.context, "Contaseña es obligatoria", Toast.LENGTH_SHORT).show()
        }
    }

  private  fun loginUser() {
      val correo = _binding.campoCorreoElectronico.editText!!.text.toString()
      val password = _binding.campoContra.editText!!.text.toString()
        auth.signInWithEmailAndPassword(correo, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("INFO", "inicio de session bueno ")
                Toast.makeText(_binding.root.context, "Inicio de session correcto", Toast.LENGTH_SHORT).show()
                _binding.root.findNavController().navigate(R.id.nav_home)
            } else {
                Log.d("INFO", "Datos incorrectos ")
                Toast.makeText(
                    _binding.root.context,
                    "Error revise al iniciar Sesión revise los datos ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun toHome() = _binding.root.findNavController().navigate(R.id.nav_home)




}