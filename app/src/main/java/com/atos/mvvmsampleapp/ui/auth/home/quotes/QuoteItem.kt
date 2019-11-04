package com.atos.mvvmsampleapp.ui.auth.home.quotes

import com.atos.mvvmsampleapp.R
import com.atos.mvvmsampleapp.data.db.entities.Quote
import com.atos.mvvmsampleapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(

    private val quote: Quote

) : BindableItem<ItemQuoteBinding>(){

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {

        viewBinding.setQuote(quote)
    }

}