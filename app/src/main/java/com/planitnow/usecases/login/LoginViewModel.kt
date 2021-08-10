package com.planitnow.usecases.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.coroutineScope
import okhttp3.Dispatcher

class LoginViewModel : ViewModel() {
    var maxAttempts = 3;

    fun decreaseAttempts(){
        if(maxAttempts > 0){
            maxAttempts--
        } else{


        }

        }

    fun  hasMaxAttempts() : Boolean{
        return maxAttempts > 0
    }
    }
