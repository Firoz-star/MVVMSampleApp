package com.atos.mvvmsampleapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.atos.mvvmsampleapp.data.repositories.UserRepository
import com.atos.mvvmsampleapp.util.ApiException
import com.atos.mvvmsampleapp.util.Coroutines

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {

            try {
                val authResponse = repository.userLogin(email!!,password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e:ApiException){
                authListener?.onFailure(e.message!!)
            }
        }


    }
}