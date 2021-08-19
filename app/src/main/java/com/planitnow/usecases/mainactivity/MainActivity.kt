package com.planitnow.usecases.mainactivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.planitnow.R
import com.planitnow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_friends
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)

        val imageView = findViewById<ImageView>(R.id.app_logo)
        imageView.setOnClickListener(){
            Toast.makeText(this,"clickado",Toast.LENGTH_SHORT).show()
        }

        navView.setupWithNavController(navController)
    }


}