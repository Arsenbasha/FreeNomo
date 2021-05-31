package triocalavera.freenomo

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import triocalavera.freenomo.databinding.ActivityMainBinding
import java.util.*
import java.util.zip.Inflater


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navigationController: NavController = findNavController(R.id.nav_host_fragment)
        setupBottomNav(navigationController)

        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        val value = sharedPreferences.getBoolean("modoOscuro", false)
        if (value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val idioma = sharedPreferences.getString("idioma","es")

    }

    private fun setupBottomNav(navigationController: NavController) = binding.bottomNavigation.let {
        NavigationUI.setupWithNavController(
            it,
            navigationController
        )
    }

    override fun getSharedPreferences(name: String?, mode: Int): SharedPreferences? {
        when (name) {
            "es" -> {
                setLocale("es")
            }
            "en" -> {
                setLocale("en")
            }
            "ca" -> {
                setLocale("ca")
            }
            else -> return super.getSharedPreferences(name, mode)

        }
        return super.getSharedPreferences(name, mode)
    }

    fun setLocale(localeName: String) {
        val myLocale = Locale(localeName)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, MainActivity::class.java)
        refresh.putExtra(localeName, localeName)
        finish()
        startActivity(refresh)
    }


}