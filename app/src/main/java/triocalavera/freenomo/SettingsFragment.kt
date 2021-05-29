package triocalavera.freenomo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }
}