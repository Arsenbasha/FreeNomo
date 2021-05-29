package triocalavera.freenomo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var auth: FirebaseAuth
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val miCuenta: Preference? = findPreference("miCuenta")
        miCuenta?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.datosUsuario)
            true
        }

        val preferenceIdioma: ListPreference? = findPreference("idioma")

        preferenceIdioma?.setOnPreferenceChangeListener { preference, newValue ->
            activity?.getSharedPreferences(newValue.toString(), 0)
            true
        }

        val cerrarSesion: Preference? = findPreference("cerrarSesion")
        if (auth.currentUser == null)
            cerrarSesion!!.isEnabled = false
        cerrarSesion!!.setOnPreferenceClickListener {
            signOut()
            true
        }


    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(context, "Se ha cerrado sesion correctamente ", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.nav_home)
    }
}