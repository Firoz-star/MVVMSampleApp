package com.atos.mvvmsampleapp.ui.auth.home.profile

import androidx.lifecycle.ViewModel
import com.atos.mvvmsampleapp.data.repositories.UserRepository

class ProfileViewModel(
   repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}
