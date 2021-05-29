package triocalavera.freenomo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import triocalavera.freenomo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(this.layoutInflater)
        val view = binding.root
        setContentView(view.rootView)
        var navigationController: NavController = findNavController(R.id.nav_host_fragment)
        setupBottomNav(navigationController)
//var crearPostItem:MenuItem =findViewById<Menu>(R.menu.main)

    }

    private fun setupBottomNav(navigationController: NavController) {
        binding.bottomNavigation.let {
          //  if (it.selectedItemId==R.id.login)
            NavigationUI.setupWithNavController(
                it,
                navigationController
            )
        }
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}