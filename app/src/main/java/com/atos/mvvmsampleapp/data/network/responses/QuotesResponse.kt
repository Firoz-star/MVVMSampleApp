package com.atos.mvvmsampleapp.data.network.responses

import com.atos.mvvmsampleapp.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)