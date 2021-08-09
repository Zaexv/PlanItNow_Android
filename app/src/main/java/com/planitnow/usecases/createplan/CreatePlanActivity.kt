package com.planitnow.usecases.createplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.planitnow.databinding.ActivityCreatePlanBinding
import com.planitnow.databinding.ActivityViewPlanBinding

class CreatePlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}