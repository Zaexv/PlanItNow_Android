package com.planitnow.usecases.createplan

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.graphql.models.CreatePlanMutation
import com.planitnow.R
import com.planitnow.databinding.ActivityCreatePlanBinding

class CreatePlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePlanBinding.inflate(layoutInflater)

        binding.viewCpInitHour.setOnClickListener(){
            buildTimePicker(binding.viewCpInitHour)
        }

        binding.viewCpEndHour.setOnClickListener(){
            buildTimePicker(binding.viewCpEndHour)
        }

        setContentView(binding.root)

    }

    private fun buildTimePicker(view : EditText){
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            val timeString = "$hour:$minute"
            view.setText(timeString)
        }
        TimePickerDialog(this, timeSetListener,4,20,true).show()

    }

}