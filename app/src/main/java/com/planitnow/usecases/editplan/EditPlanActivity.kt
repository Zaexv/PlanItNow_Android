package com.planitnow.usecases.editplan

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.graphql.models.DetailedPlanQuery
import com.planitnow.R
import com.planitnow.databinding.ActivityCreatePlanBinding
import com.planitnow.usecases.viewplan.ViewPlanViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EditPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanBinding
    private val editPlanViewModel: EditPlanViewModel by viewModels()
    private var calendar: Calendar = Calendar.getInstance()
    private  var publicActive: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePlanBinding.inflate(layoutInflater)

        val planID = intent.getStringExtra("id")
        lifecycleScope.launchWhenResumed {
            editPlanViewModel.getPlanById(planID!!)
            bindPlan(editPlanViewModel.detailedPlan)
        }
        initializeView()

        setContentView(binding.root)

        initializeToolbar()

    }

    private fun bindPlan(detailedPlan: DetailedPlanQuery.DetailedPlan) {
        binding.viewCpTitle.setText(detailedPlan.title)
        binding.viewCpDescription.setText(detailedPlan.description)
        binding.viewCpLocation.setText(detailedPlan.location)
        binding.viewCpInitHour.setText(detailedPlan.initHour.toString())
        binding.viewCpEndHour.setText(detailedPlan.endHour.toString())
        binding.viewCpMaxParticipants.setText(detailedPlan.maxParticipants.toString())
        binding.viewCpImageURL.setText(detailedPlan.urlPlanPicture)
        var parsedDate = LocalDate.parse(
            detailedPlan.initDate.toString(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
        calendar.set(Calendar.YEAR, parsedDate.year);
        calendar.set(Calendar.MONTH, (parsedDate.monthValue - 1));
        calendar.set(Calendar.DAY_OF_MONTH, parsedDate.dayOfMonth);
        val milliTime = calendar.timeInMillis
        binding.viewCpInitDate.setDate(milliTime,true,true)
    }

    private fun initializeView() {
        binding.viewCpInitHour.setOnClickListener() {
            buildTimePicker(binding.viewCpInitHour)
        }

        binding.viewCpEndHour.setOnClickListener() {
            buildTimePicker(binding.viewCpEndHour)
        }

        binding.viewCpButton.text = getText(R.string.edit)
        binding.viewCpButton.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_edit_black_24dp,0,0,0
        )

        binding.viewCpButton.setOnClickListener() {
            editPlan()
        }

        binding.viewCpInitDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }

        binding.viewCpPublic.setOnCheckedChangeListener{ _, isChecked ->
            publicActive = isChecked
        }
    }

    private fun editPlan() {

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