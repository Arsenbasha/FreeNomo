package triocalavera.freenomo

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import triocalavera.freenomo.databinding.ActivityMainBinding
import java.util.zip.Inflater


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root);
        val navigationController: NavController = findNavController(R.id.nav_host_fragment)
        setupBottomNav(navigationController)
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        val value = sharedPreferences.getBoolean("modoOscuro", false)
        if (value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

    private fun setupBottomNav(navigationController: NavController) = binding.bottomNavigation.let {
        NavigationUI.setupWithNavController(
            it,
            navigationController
        )
    }
}