package com.atos.mvvmsampleapp.ui.auth.home.quotes

import androidx.lifecycle.ViewModel
import com.atos.mvvmsampleapp.data.repositories.QuotesRepository
import com.atos.mvvmsampleapp.util.lazyDeferred

class QuotesViewModel(

    repository: QuotesRepository

) : ViewModel() {

    val quotes by lazyDeferred{
        repository.getQuotes()
    }
}
