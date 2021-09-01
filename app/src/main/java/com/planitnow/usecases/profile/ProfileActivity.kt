package com.planitnow.usecases.profile

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.api.load
import coil.transform.CircleCropTransformation
import com.planitnow.R
import com.planitnow.databinding.ActivityProfileBinding
import com.planitnow.databinding.ActivityRegisterBinding
import com.planitnow.model.session.Session
import com.planitnow.usecases.login.LoginActivity
import com.planitnow.usecases.login.LoginRouter
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)

        initializeView()
        initializeButtons()

        setContentView(binding.root)

    }

    private fun initializeView() {
        binding.profileInputUsername.setText(Session.instance.me.userProfile?.publicUsername)
        binding.profileInputBiography.setText(Session.instance.me.userProfile?.biography)
        binding.profileInputResidence.setText(Session.instance.me.userProfile?.residence)
        binding.profileInputImageUrl.setText(Session.instance.me.userProfile?.urlProfilePicture)


        binding.activityAppLogo.load(Session.instance.me.userProfile?.urlProfilePicture){
            transformations(CircleCropTransformation())
        }

    }

    private fun initializeButtons() {
        binding.editprofileButtonCancel.setOnClickListener() {
            this.finish()
        }

        binding.editprofileButtonConfirm.setOnClickListener() {
            lifecycleScope.launchWhenResumed {

            }
        }

        binding.logoutButton.setOnClickListener(){
            removeTokenFromSharedPreferences()
            Toast.makeText(this@ProfileActivity, getText(R.string.logout_action), Toast.LENGTH_SHORT).show()
            this.finish()
        }

        binding.profileInputImageUrl.setOnFocusChangeListener{view, boolean ->
            binding.activityAppLogo.load(binding.profileInputImageUrl.text.toString()){
                transformations(CircleCropTransformation())
            }
        }

    }

    private fun removeTokenFromSharedPreferences() {
        val preferences = this.getSharedPreferences("planitnow", Context.MODE_PRIVATE)
        preferences.edit().putString("TOKEN", "null").apply();
    }


}