package com.planitnow.usecases.mainactivity

import android.content.Context
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
import coil.api.load
import coil.transform.CircleCropTransformation
import com.planitnow.R
import com.planitnow.databinding.ActivityMainBinding
import com.planitnow.model.session.Session

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

        imageView.load(Session.instance.me.userProfile!!.urlProfilePicture){
            transformations(CircleCropTransformation())
        }

        imageView.setOnClickListener(){
            Toast.makeText(this,"Has cerrado sesión",Toast.LENGTH_SHORT).show()
            removeTokenFromSharedPreferences()
        }

        navView.setupWithNavController(navController)
    }

    //TODO refactor al edit profile
    private fun removeTokenFromSharedPreferences(){
        val preferences = this.getSharedPreferences("planitnow", Context.MODE_PRIVATE)
        preferences.edit().putString("TOKEN", "null").apply();
    }

}