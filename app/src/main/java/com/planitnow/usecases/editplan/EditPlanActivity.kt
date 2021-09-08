package com.planitnow.usecases.editplan

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.R
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.databinding.ActivityCreatePlanBinding
import com.planitnow.usecases.mainactivity.MainActivityRouter
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class EditPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanBinding
    private var calendar: Calendar = Calendar.getInstance()
    private  var publicActive: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePlanBinding.inflate(layoutInflater)

        binding.viewCpInitHour.setOnClickListener() {
            buildTimePicker(binding.viewCpInitHour)
        }

        binding.viewCpEndHour.setOnClickListener() {
            buildTimePicker(binding.viewCpEndHour)
        }

        binding.viewCpButton.text = getText(R.string.edit)

        binding.viewCpButton.setOnClickListener() {
            createPlan()
        }

        binding.viewCpInitDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }

        binding.viewCpPublic.setOnCheckedChangeListener{ _, isChecked ->
            publicActive = isChecked
        }


        setContentView(binding.root)

        initializeToolbar()

    }

    private fun createPlan() {
        val title = binding.viewCpTitle.text.toString()
        val description = binding.viewCpDescription.text.toString()
        val location = binding.viewCpLocation.text.toString()
        val initHour = binding.viewCpInitHour.text.toString()
        val endHour = binding.viewCpEndHour.text.toString()
        val initDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
        val urlPlanPicture = binding.viewCpImageURL.text.toString()
        var maxParticipants = binding.viewCpMaxParticipants.text.toString()
        if (maxParticipants == "") maxParticipants = "5"
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
                    maxParticipants = Integer.parseInt(maxParticipants),
                    isPublic = publicActive,
                    urlPlanPicture = urlPlanPicture
                )
                successQuery = true
            } catch(e: ApolloException) {
                println("APOLLO ERRORS:" + e.message)
            }
        }

        if(successQuery) {
            Toast.makeText(this, R.string.success_creating_plan, Toast.LENGTH_SHORT).show()
            this.finish()
        }  else {
            Toast.makeText(this, R.string.error_creating_plan, Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildTimePicker(view: EditText) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            val hourString = if(hour < 10) "0$hour" else "$hour"
            val minuteString = if(minute < 10) "0$minute" else "$minute"
            val timeString = "$hourString:$minuteString"
            view.setText(timeString)
        }
        TimePickerDialog(this, timeSetListener, 4, 20, true).show()
    }

    private fun initializeToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        findViewById<ImageView>(R.id.profile_logo).visibility = View.GONE
        findViewById<ImageView>(R.id.app_logo).visibility = View.GONE
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}