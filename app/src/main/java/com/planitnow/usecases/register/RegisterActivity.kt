package com.planitnow.usecases.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.planitnow.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private var calendar: Calendar = Calendar.getInstance()

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
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val birthDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
                binding.registerInputBirthdate.setText(birthDate)
            }
        DatePickerDialog(this, dateSetListener, 2020, 9, 25).show()
    }


}