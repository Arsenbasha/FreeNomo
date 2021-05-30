package triocalavera.freenomo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var auth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible =
            true
    }
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
        if (auth.currentUser == null)  {
            cerrarSesion!!.isEnabled = false
            miCuenta!!.isEnabled = false
        } else {
            miCuenta!!.isEnabled = true
        }


        cerrarSesion!!.setOnPreferenceClickListener {
            signOut()
            true

        }

        val modoOscuro: CheckBoxPreference? = findPreference("modoOscuro")

        modoOscuro?.setOnPreferenceClickListener {
            if (modoOscuro.isChecked) {
                Toast.makeText(context, "Modo oscuro activado", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                Toast.makeText(context, "Modo oscuro desactivado", Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }


    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(context, "Se ha cerrado sesión correctamente", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.nav_home)
    }
}