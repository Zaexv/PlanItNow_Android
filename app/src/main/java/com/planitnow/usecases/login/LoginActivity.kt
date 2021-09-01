package com.planitnow.usecases.login

import android.os.Bundle
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
        }
        if (logged) {
            MainActivityRouter().launch(this)
        } else {
            Toast.makeText(this, getText(R.string.login_error), Toast.LENGTH_LONG).show()
        }
    }

    private fun showMaxAttemptsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.loginmaxAttempts)
        builder.setMessage(R.string.loginMaxAttemptsMessage)
        builder.setPositiveButton(R.string.accept, null).show()
    }

}