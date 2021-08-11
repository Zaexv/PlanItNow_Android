package com.planitnow.usecases.createplan

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.R
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.databinding.ActivityCreatePlanBinding
import com.planitnow.usecases.mainactivity.MainActivityRouter
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class CreatePlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanBinding
    private var calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePlanBinding.inflate(layoutInflater)

        binding.viewCpInitHour.setOnClickListener() {
            buildTimePicker(binding.viewCpInitHour)
        }

        binding.viewCpEndHour.setOnClickListener() {
            buildTimePicker(binding.viewCpEndHour)
        }

        binding.viewCpButton.setOnClickListener() {
            createPlan()
        }

        binding.viewCpInitDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }

        setContentView(binding.root)

    }

    private fun createPlan() {
        val title = binding.viewCpTitle.text.toString()
        val description = binding.viewCpDescription.text.toString()
        val location = binding.viewCpLocation.text.toString()
        val initHour = binding.viewCpInitHour.text.toString()
        val endHour = binding.viewCpEndHour.text.toString()
        val isPublic = binding.viewCpPublic.isActivated
        val initDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

        //TODO refactor a ViewModel
        var successQuery = false
        runBlocking {
            try {
                ApolloMutationHandler.createPlan(
                    description = description,
                    title = title,
                    location = location,
                    initHour = initHour,
                    endHour = endHour,
                    initDate = initDate,
                    isPublic = isPublic
                )
                successQuery = true
            } catch(e: ApolloException) {
                println("APOLLO ERRORS:" + e.message)
            }
        }

        if(successQuery) {
            Toast.makeText(this, R.string.success_creating_plan, Toast.LENGTH_LONG).show()
            MainActivityRouter().launch(this)
        }  else {
            Toast.makeText(this, R.string.error_creating_plan, Toast.LENGTH_LONG).show()
        }
    }

    private fun buildTimePicker(view: EditText) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            val timeString = if (hour < 10)  "0$hour:$minute" else "$hour:$minute"
            view.setText(timeString)
        }
        TimePickerDialog(this, timeSetListener, 4, 20, true).show()
    }

}