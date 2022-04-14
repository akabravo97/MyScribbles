package com.example.myscribbles.repository

import com.example.myscribbles.api.QuotesApi
import com.example.myscribbles.model.Quote
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesRepository @Inject constructor(private val quotesApi: QuotesApi) {

    suspend fun getQuote(maxLength: Int): Response<Quote> {
        return quotesApi.getQuote(maxLength)
    }
}