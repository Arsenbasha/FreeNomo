package triocalavera.freenomo.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import triocalavera.freenomo.R
import triocalavera.freenomo.databinding.DatosUsuarioFragmentBinding

class DatosUsuarioViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var _binding: DatosUsuarioFragmentBinding


    fun init(binding: DatosUsuarioFragmentBinding) {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        _binding = binding
    }


    fun alertaCambiarNombre(view: View, requireActivity: FragmentActivity) {

        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;

        val inflater2 = inflater.inflate(R.layout.alerta_nombre, null)
        val nombre = inflater2.findViewById<EditText>(R.id.cambiarNombre)
        val currPswd = inflater2.findViewById<EditText>(R.id.pswdCN)

        builder.setTitle("Cambiar nombre")
        builder.setMessage("Recuerda poner tu nombre y apellidos completos para poder cambiar tu nombre a la vista de los demás.")
        builder.setView(inflater2)
        builder.setPositiveButton(
            "Cambiar nombre"
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            if(nombre.text.isNotEmpty() && currPswd.text.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currPswd.text.toString())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(_binding.root.context, "Re-Authentication succes.", Toast.LENGTH_SHORT).show()
                                val map: MutableMap<String, Any?> = HashMap()
                                map["nombreCompleto"] = nombre.text.toString()
                                database.child("Users").child(user.uid)
                                    .updateChildren(map)
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d("INFO", "Tu nombre a sido cambiado correctamente.")

                                        } else {
                                            Log.d("INFO", "Error")
                                        }
                                    }
                            } else {
                                Log.d("INFO", "Error")
                            }
                        }
                }
            } else {
                Toast.makeText(_binding.root.context, "Porfavor rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar"  ) {
                dialog, id ->
        }
        builder.show()
    }
    fun alertaCambiarCorreo(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;

        val inflater2 = inflater.inflate(R.layout.alerta_correo, null)
        val correo = inflater2.findViewById<EditText>(R.id.correoE)
        val currPswd = inflater2.findViewById<EditText>(R.id.pswdCE)
        builder.setTitle("Cambiar correo")
        builder.setMessage("Cambiar el correo electronico'")
        builder.setView(inflater2)
        builder.setPositiveButton(
            "Cambiar correo"
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            if(correo.text.isNotEmpty() && currPswd.text.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currPswd.text.toString())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                user.updateEmail(correo.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val map: MutableMap<String, Any?> = HashMap()
                                            map["correo"] = correo.text.toString()
                                            database.child("Users").child(user.uid)
                                                .updateChildren(map)
                                            Log.d("INFO", "Se a cambiado el correo correctamente.")

                                        } else {
                                            Log.d("INFO", "Error")
                                        }
                                    }
                            } else {
                                Log.d("INFO", "Error")
                            }
                        }
                }
            } else {
                Toast.makeText(_binding.root.context, "Porfavor no dejes ningún campo vacío.", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, id ->
        }
        builder.show()
    }
    fun alertaCambiarTelefono(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;

        val inflater2 = inflater.inflate(R.layout.alerta_telefono, null)
        val nombre = inflater2.findViewById<EditText>(R.id.oldTlf)
        val currPswd = inflater2.findViewById<EditText>(R.id.pswdCE)

        builder.setTitle("Cambiar telfono")
        builder.setMessage("Introduce tu número de teléfono.")
        builder.setView(inflater2)
        builder.setPositiveButton(
            "Cambiar teléfono"
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            if(nombre.text.isNotEmpty() && currPswd.text.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currPswd.text.toString())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(_binding.root.context, "Re-Authentication succes.", Toast.LENGTH_SHORT).show()
                                val map: MutableMap<String, Any?> = HashMap()
                                map["telefono"] = nombre.text.toString()
                                database.child("Users").child(user.uid)
                                    .updateChildren(map)
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d("INFO", "Tu teléfono a sido cambiado correctamente.")

                                        } else {
                                            Log.d("INFO", "Error")
                                        }
                                    }
                            } else {
                                Log.d("INFO", "Error")
                            }
                        }
                }
            } else {
                Toast.makeText(_binding.root.context, "Porfavor rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar"  ) {
                dialog, id ->
        }
        builder.show()
    }
    fun alertaCambiarContra(view: View, requireActivity: FragmentActivity) {

        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.alerta_contrasena,null)
        val oldPswd = inflater2.findViewById<EditText>(R.id.contrasenaA)
        val newPsw = inflater2.findViewById<EditText>(R.id.contrasenaN)
        val repeatPsw = inflater2.findViewById<EditText>(R.id.contrasenaNR)
        builder.setTitle("Cambiar contraseña")
        builder.setMessage("Introduce tu contraseña dos veces para asegurarnos que eres el propietario de esta cuenta.")
        builder.setView(inflater2)
        builder.setPositiveButton(
            "Cambiar ahora"
        ) { dialog, id ->
            auth = FirebaseAuth.getInstance()
            if (oldPswd.text.isNotEmpty() && newPsw.text.isNotEmpty() && repeatPsw.text.isNotEmpty()) {
                if (newPsw.text.toString().equals(repeatPsw.text.toString())) {
                    val user = auth.currentUser
                    if (user != null && user.email != null) {
                        val credential = EmailAuthProvider
                            .getCredential(user.email!!, oldPswd.text.toString())
                        user.reauthenticate(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(_binding.root.context,"Re-Authentication succes.",Toast.LENGTH_SHORT).show()
                                    user.updatePassword(newPsw.text.toString())
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d("INFO", "Tu contraseña se a modificado correctamente.")
                                                auth.signOut()
                                                _binding.root.findNavController().navigate(R.id.nav_home)
                                            }
                                        }
                                } else {
                                    Toast.makeText(
                                        _binding.root.context,
                                        "Re-Authentication succes failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            } else {
                Toast.makeText(_binding.root.context, "Porfavor no deje campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, id ->
        }
        builder.show()
    }
    fun alertaBorrarCuenta(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.alerta_borrar_cuenta,null)
        val editText = inflater2.findViewById<EditText>(R.id.pwsdUser)
        builder.setTitle("Borrar cuenta")
        builder.setMessage("¿Estás seguro de que quieres borrar la cuenta? Una vez borrada no podrás volver a acceder a ella")
        builder.setView(inflater2)
        builder.setPositiveButton(
            "Borrar Cuenta"
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            val password = editText.text.toString()
            val credenciales =
                EmailAuthProvider.getCredential(auth.currentUser!!.email.toString(), password)
            auth.currentUser!!.reauthenticate(credenciales).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database.child("Users").child(auth.currentUser!!.uid).removeValue().addOnSuccessListener{
                        auth.currentUser!!.delete().addOnSuccessListener{
                            Toast.makeText(
                                _binding.root.context,
                                "Tu cuenta a sido borrada correctamente.",
                                Toast.LENGTH_SHORT
                            ).show()
                             _binding.root.findNavController().navigate(R.id.nav_home)
                        }
                    }
                } else {
                    Log.d("INFO", "Error ${task.result}")
                }
            }
        }
        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, id ->
        }
        builder.show()
    }

    fun irAjustes() = _binding.root.findNavController().navigate(R.id.settingsFragment)

}