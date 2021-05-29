package triocalavera.freenomo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import triocalavera.freenomo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        val view = binding.root
        setContentView(view.rootView)
        val navigationController: NavController = findNavController(R.id.nav_host_fragment)
        setupBottomNav(navigationController)
    }
    private fun setupBottomNav(navigationController: NavController) = binding.bottomNavigation.let {NavigationUI.setupWithNavController(it, navigationController)}
}