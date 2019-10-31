package com.atos.mvvmsampleapp.ui.auth

import com.atos.mvvmsampleapp.data.db.entities.User


interface AuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}