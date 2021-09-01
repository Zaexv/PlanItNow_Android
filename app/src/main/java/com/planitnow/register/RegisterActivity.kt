package com.planitnow.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.R
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.databinding.ActivityCreatePlanBinding
import com.planitnow.databinding.ActivityRegisterBinding
import com.planitnow.usecases.login.LoginViewModel
import com.planitnow.usecases.mainactivity.MainActivityRouter
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        bindData()


        setContentView(binding.root)

    }

    private fun bindData() {
        binding.registerButtonCancel.setOnClickListener() {
            this.finish()
        }

        binding.registerButtonConfirm.setOnClickListener() {
            lifecycleScope.launchWhenResumed {
                val registered = registerViewModel.doRegister(
                    this@RegisterActivity,
                    binding.registerInputPassword.text.toString(),
                    binding.registerInputConfirmpassword.text.toString(),
                    binding.registerInputUsername.text.toString(),
                    binding.registerInputBirthdate.text.toString(),
                    binding.registerInputEmail.text.toString(),
                    binding.registerInputName.text.toString(),
                    binding.registerInputLastname.text.toString(),
                )

                if (registered) this@RegisterActivity.finish()
            }
        }

        binding.registerInputBirthdate.setOnClickListener() {
            buildDatePicker(binding.registerInputBirthdate)
        }

    }

    private fun buildDatePicker(view: EditText) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                val dayString = if (day < 10) "0$day" else "$day"
                val monthString = if (month < 10) "0$month" else "$month"
                view.setText("$year-$monthString-$dayString")
            }
        DatePickerDialog(this, dateSetListener, 2020, 9, 25).show()
    }


}