package com.messiasjunior.codewarsv2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.messiasjunior.codewarsv2.R

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
    }

    private fun setupActionBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
        val navigationController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navigationController.graph)
        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navigationController = findNavController(R.id.mainNavHost)
        return navigationController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
