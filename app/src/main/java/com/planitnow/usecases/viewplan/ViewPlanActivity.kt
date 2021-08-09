package com.planitnow.usecases.viewplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.planitnow.R
import com.planitnow.databinding.ActivityMainBinding
import com.planitnow.databinding.ActivityViewPlanBinding

class ViewPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}