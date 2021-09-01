package com.planitnow.usecases.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.backend.ApolloLoginHandler

class RegisterViewModel : ViewModel() {

    suspend fun doRegister(
        c: Context,
        password: String,
        confirmPassword: String,
        username: String,
        birthDate: String,
        email: String,
        firstName: String,
        lastName: String
    ): Boolean {
        try {
            if (password == confirmPassword) {
                val res = ApolloLoginHandler.register(
                    password,
                    username,
                    birthDate,
                    email,
                    firstName,
                    lastName
                )
                return if (res.hasErrors()) {
                    Toast.makeText(c, "Error: " + res.errors!![0].message, Toast.LENGTH_SHORT).show()
                    false
                } else {
                    Toast.makeText(c, "Te has registrado con éxito.", Toast.LENGTH_SHORT).show()
                    true
                }
            } else {
                Toast.makeText(c, "Error: La contraseña no coincide. Revísala", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            return false
        } catch (e: ApolloException) {
            Toast.makeText(c, e.message, Toast.LENGTH_SHORT).show()
        }
        return false
    }

}