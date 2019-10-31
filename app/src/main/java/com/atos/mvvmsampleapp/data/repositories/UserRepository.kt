package com.atos.mvvmsampleapp.data.repositories

import com.atos.mvvmsampleapp.data.db.AppDatabase
import com.atos.mvvmsampleapp.data.db.entities.User
import com.atos.mvvmsampleapp.data.network.MyApi
import com.atos.mvvmsampleapp.data.network.SafeApiRequest
import com.atos.mvvmsampleapp.data.network.responses.AuthResponses

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase

) : SafeApiRequest(){

    suspend fun userLogin(email: String, password: String): AuthResponses {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ):AuthResponses{
        return apiRequest { api.userSignup(name,email,password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

}