package com.evantemplate.cats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.RequestManager
import com.evantemplate.cats.di.DaggerAppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfig = AppBarConfiguration.Builder(
            R.id.allCatsFragment,
            R.id.favoriteCatsFragment
        ).build()

        bottomNavView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}