package com.planitnow.usecases.login

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.planitnow.databinding.ActivityLoginBinding
import com.planitnow.databinding.ActivityViewPlanBinding
import com.planitnow.databinding.FragmentHomeBinding
import com.planitnow.model.session.Session
import com.planitnow.usecases.homefeed.HomeViewModel
import com.planitnow.usecases.mainactivity.MainActivityRouter
import kotlinx.coroutines.runBlocking

class LoginActivity: AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener(){

            val ok = lifecycleScope.launchWhenResumed {
                val user = binding.loginEmail.text.toString()
                val password = binding.loginPassword.text.toString()
                Session.instance.doLogin(user,password)
            }
            //TODO hacer que sólo pase a la siguiente actividad si estoy loggeado
            if(!Session.instance.getToken().isEmpty()){
                MainActivityRouter().launch(this)
            }

        }

        setContentView(binding.root)
    }

}