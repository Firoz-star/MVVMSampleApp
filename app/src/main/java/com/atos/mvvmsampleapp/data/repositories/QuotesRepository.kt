package com.atos.mvvmsampleapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atos.mvvmsampleapp.data.db.AppDatabase
import com.atos.mvvmsampleapp.data.db.entities.Quote
import com.atos.mvvmsampleapp.data.network.MyApi
import com.atos.mvvmsampleapp.data.network.SafeApiRequest
import com.atos.mvvmsampleapp.data.preferences.PreferencesProvider
import com.atos.mvvmsampleapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


private val MINIMUM_INTERVAL = 6

class QuotesRepository(

    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferencesProvider

) : SafeApiRequest(){

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever{
            saveQuotes(it)
        }
    }

    suspend fun getQuotes() : LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes(){

        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean{
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>){

        Coroutines.io{
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}