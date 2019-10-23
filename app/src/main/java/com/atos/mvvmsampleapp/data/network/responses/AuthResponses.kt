package com.atos.mvvmsampleapp.data.network.responses

import com.atos.mvvmsampleapp.data.db.entities.User

data class AuthResponses(

    val isSuccessful : Boolean?,
    val message : String?,
    val user : User?
)