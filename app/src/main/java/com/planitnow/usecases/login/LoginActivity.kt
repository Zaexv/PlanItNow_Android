package com.planitnow.usecases.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.R
import com.planitnow.databinding.ActivityLoginBinding
import com.planitnow.model.session.Session
import com.planitnow.register.RegisterRouter
import com.planitnow.usecases.mainactivity.MainActivityRouter
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private var maxAttempts = 3;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        initializeAppFromToken()

        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener() {
            if (loginViewModel.hasMaxAttempts()) {
                login();
            } else {
                showMaxAttemptsDialog()
            }
        }

        binding.registrationText.setOnClickListener() {
            RegisterRouter().launch(this)
        }

        setContentView(binding.root)
    }

    private fun login() {
        var logged = false;
        lifecycleScope.launchWhenResumed {
            val user = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            try {
                logged = Session.instance.doLogin(user, password)
            } catch (e: ApolloException) {
                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
            }
            loginViewModel.decreaseAttempts()
            if (logged) {
                addTokenToSharedPreferences()
                MainActivityRouter().launch(this@LoginActivity)
            } else {
                Toast.makeText(this@LoginActivity, getText(R.string.login_error), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun showMaxAttemptsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.loginmaxAttempts)
        builder.setMessage(R.string.loginMaxAttemptsMessage)
        builder.setPositiveButton(R.string.accept, null).show()
    }


    //TODO cambiar el nombre de las shared preferences
    private fun addTokenToSharedPreferences(){
        val preferences = this.getSharedPreferences("planitnow", Context.MODE_PRIVATE)
        preferences.edit().putString("TOKEN",Session.instance.getToken()).apply();
    }

    private fun loadTokenFromSharedPreferences(): String {
        val preferences = this.getSharedPreferences("planitnow", Context.MODE_PRIVATE)
        Toast.makeText(this,preferences.getString("TOKEN",null), Toast.LENGTH_SHORT).show()
        return preferences.getString("TOKEN","null")!!;
    }

    private fun initializeAppFromToken(){
        val token = loadTokenFromSharedPreferences()
        lifecycleScope.launchWhenResumed {
            val verified = Session.instance.verifyToken(token)
            if (verified) {MainActivityRouter().launch(this@LoginActivity)}
            else {
                binding.loginEmail.visibility = View.VISIBLE
                binding.loginPassword.visibility = View.VISIBLE
                binding.loginButton.visibility = View.VISIBLE
                binding.registrationText.visibility = View.VISIBLE
            }
        }
    }

}