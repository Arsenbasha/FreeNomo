package triocalavera.freenomo.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
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

        builder.setTitle(R.string.cambiarNombre)
        builder.setMessage(R.string.recuerda)
        builder.setView(inflater2)
        builder.setPositiveButton(
            R.string.cambiarNombre
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            if (nombre.text.isNotEmpty() && currPswd.text.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currPswd.text.toString())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                popUpNombre(view, requireActivity)
                                val map: MutableMap<String, Any?> = HashMap()
                                map["nombreCompleto"] = nombre.text.toString()
                                database.child("Users").child(user.uid)
                                    .updateChildren(map)
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {

                                        } else {
                                            Log.d("INFO", "Error")
                                        }
                                    }
                            } else {
                                popUpError(view, requireActivity)
                            }
                        }
                }
            } else {
                Toast.makeText(
                    _binding.root.context,
                    R.string.rellena,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(R.string.btnNoCancelar) { dialog, id ->
        }
        builder.show()
    }

    fun alertaCambiarCorreo(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;

        val inflater2 = inflater.inflate(R.layout.alerta_correo, null)
        val correo = inflater2.findViewById<EditText>(R.id.correoE)
        val currPswd = inflater2.findViewById<EditText>(R.id.pswdCE)
        builder.setTitle(R.string.cambiarCorreo)
        builder.setMessage(R.string.cambiarCorreoElectronico)
        builder.setView(inflater2)
        builder.setPositiveButton(
            R.string.cambiarCorreo
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            if (correo.text.isNotEmpty() && currPswd.text.isNotEmpty()) {
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
                                            popUpCorreo(view, requireActivity)
                                            val map: MutableMap<String, Any?> = HashMap()
                                            map["correo"] = correo.text.toString()
                                            database.child("Users").child(user.uid)
                                                .updateChildren(map)
                                        } else {
                                            Log.d("INFO", "Error")
                                        }
                                    }
                            } else {
                                popUpError(view, requireActivity)
                            }
                        }
                }
            } else {
                Toast.makeText(
                    _binding.root.context,
                    R.string.camposVacios,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(
            R.string.btnNoCancelar
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

        builder.setTitle(R.string.cambiarTelefono)
        builder.setMessage(R.string.numTelefono)
        builder.setView(inflater2)
        builder.setPositiveButton(
            R.string.cambiarTelefono
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            if (nombre.text.isNotEmpty() && currPswd.text.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currPswd.text.toString())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                popUpTelefono(view, requireActivity)
                                val map: MutableMap<String, Any?> = HashMap()
                                map["telefono"] = nombre.text.toString()
                                database.child("Users").child(user.uid)
                                    .updateChildren(map)
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {

                                        } else {
                                            Log.d("INFO", "Error")
                                        }
                                    }
                            } else {
                                popUpError(view, requireActivity)
                            }
                        }
                }
            } else {
                Toast.makeText(
                    _binding.root.context,
                    R.string.rellena,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(R.string.btnNoCancelar) { dialog, id ->
        }
        builder.show()
    }

    fun alertaCambiarContra(view: View, requireActivity: FragmentActivity) {

        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.alerta_contrasena, null)
        val oldPswd = inflater2.findViewById<EditText>(R.id.contrasenaA)
        val newPsw = inflater2.findViewById<EditText>(R.id.contrasenaN)
        val repeatPsw = inflater2.findViewById<EditText>(R.id.contrasenaNR)
        builder.setTitle(R.string.cambiarContraseña)
        builder.setMessage(R.string.contraseña)
        builder.setView(inflater2)
        builder.setPositiveButton(
            R.string.cambiar
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
                                    popUpContrasena(view, requireActivity)
                                    user.updatePassword(newPsw.text.toString())
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                auth.signOut()
                                                _binding.root.findNavController()
                                                    .navigate(R.id.nav_home)
                                            }
                                        }
                                } else {
                                    popUpError(view, requireActivity)
                                }
                            }
                    }
                }
            } else {
                Toast.makeText(
                    _binding.root.context,
                    R.string.camposVacios,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(
            R.string.btnNoCancelar
        ) { dialog, id ->
        }
        builder.show()
    }

    fun alertaBorrarCuenta(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.alerta_borrar_cuenta, null)
        val editText = inflater2.findViewById<EditText>(R.id.pwsdUser)
        builder.setTitle(R.string.borrar)
        builder.setMessage(R.string.seguro)
        builder.setView(inflater2)
        builder.setPositiveButton(
            R.string.borrar
        ) { dialog, id ->
            database = Firebase.database.reference
            auth = FirebaseAuth.getInstance()
            val password = editText.text.toString()
            val credenciales =
                EmailAuthProvider.getCredential(auth.currentUser!!.email.toString(), password)
            auth.currentUser!!.reauthenticate(credenciales).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database.child("Users").child(auth.currentUser!!.uid).removeValue()
                        .addOnSuccessListener {
                            auth.currentUser!!.delete().addOnSuccessListener {
                                popUpBorrarCuenta(view, requireActivity)
                                _binding.root.findNavController().navigate(R.id.nav_home)
                            }
                        }
                } else {
                    popUpError(view, requireActivity)
                }
            }
        }
        builder.setNegativeButton(
            R.string.btnNoCancelar
        ) { dialog, id ->
        }
        builder.show()
    }

    fun popUpNombre(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.vrfc_nombre, null)

        builder.setView(inflater2)
        val dialog = builder.show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val boton = inflater2.findViewById<Button>(R.id.btnConfirmar)

        boton.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun popUpCorreo(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.vrfc_correo, null)

        builder.setView(inflater2)
        val dialog = builder.show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val boton = inflater2.findViewById<Button>(R.id.btnConfirmar)

        boton.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun popUpContrasena(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.vrfc_contrasena, null)

        builder.setView(inflater2)
        val dialog = builder.show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val boton = inflater2.findViewById<Button>(R.id.btnConfirmar)

        boton.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun popUpTelefono(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.vrfc_telefono, null)

        builder.setView(inflater2)
        val dialog = builder.show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val boton = inflater2.findViewById<Button>(R.id.btnConfirmar)

        boton.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun popUpBorrarCuenta(view: View, requireActivity: FragmentActivity) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.vrfc_borrarcuenta, null)

        builder.setView(inflater2)
        val dialog = builder.show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val boton = inflater2.findViewById<Button>(R.id.btnConfirmar)

        boton.setOnClickListener {
            dialog.dismiss()
        }

    }


    fun popUpError(view: View, requireActivity: FragmentActivity) {

        val builder = AlertDialog.Builder(view.context)
        val inflater = requireActivity.layoutInflater;
        val inflater2 = inflater.inflate(R.layout.vrfc_error, null)

        builder.setView(inflater2)
        val dialog = builder.show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val boton = inflater2.findViewById<Button>(R.id.btnConfirmar)

        boton.setOnClickListener {
            dialog.dismiss()
        }


    }

    fun irAjustes() = _binding.root.findNavController().navigate(R.id.settingsFragment)

}