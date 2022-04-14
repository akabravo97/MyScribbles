package com.example.myscribbles.api

import com.example.myscribbles.model.Quote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi {
    companion object {
        const val BASE_QUOTE_URL = "https://api.quotable.io/"
    }

    @GET("random")
    suspend fun getQuote(@Query("maxLength") maxLength: Int): Response<Quote>
}