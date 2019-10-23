package com.atos.mvvmsampleapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.atos.mvvmsampleapp.R
import com.atos.mvvmsampleapp.data.db.AppDatabase
import com.atos.mvvmsampleapp.data.db.entities.User
import com.atos.mvvmsampleapp.data.network.MyApi
import com.atos.mvvmsampleapp.data.repositories.UserRepository
import com.atos.mvvmsampleapp.databinding.ActivityLoginBinding
import com.atos.mvvmsampleapp.ui.auth.home.HomeActivity
import com.atos.mvvmsampleapp.util.hide
import com.atos.mvvmsampleapp.util.show
import com.atos.mvvmsampleapp.util.snackbar
import com.atos.mvvmsampleapp.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = MyApi()
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null){
                Intent(this,HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        //toast("Login Started...")
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        //root_layout.snackbar("${user.name} is logged in")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
