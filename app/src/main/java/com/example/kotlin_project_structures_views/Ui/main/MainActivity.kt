package com.example.kotlin_project_structure_new.Ui.main
import com.example.kotlin_project_structure_new.R
import androidx.navigation.fragment.findNavController


import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.kotlin_project_structure_new.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavController


class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        binding?.bottomNav?.let { bottomNav ->
            ViewCompat.setOnApplyWindowInsetsListener(bottomNav) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                // Only apply bottom padding for bottom navigation
                v.updatePadding(bottom = systemBars.bottom)
                insets
            }
        }

        binding?.navigationView?.let { navView ->
            ViewCompat.setOnApplyWindowInsetsListener(navView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                // Drawer extends under status bar
                v.setPadding(0, systemBars.top, 0, 0)
                insets
            }
        }





//        ViewCompat.setOnApplyWindowInsetsListener(binding?.root!!) { toolbar, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            toolbar.updatePadding(top = systemBars.top)
//            insets
//        }

        binding?.navigationView?.getHeaderView(0)?.let { header ->
            ViewCompat.setOnApplyWindowInsetsListener(header) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updatePadding(top = systemBars.top) // push header down by status bar height
                insets
            }
        }



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController  = navHostFragment.navController ;


//         Setup bottom navigation click
        binding?.bottomNav?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.home2)
                    true
                }
                R.id.aboutFragment -> {
                    navController.navigate(R.id.aboutFragment)
                    true
                }
                R.id.accountFragment -> {
                    navController.navigate(R.id.accountFragment)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav) ;
            if (destination.id == R.id.splashFragment) {

                bottomNav.visibility = View.GONE
            }else{
                bottomNav.visibility = View.VISIBLE
            }
        }


    }



    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}