package com.example.myscribbles.view.scribble

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myscribbles.model.Entry
import com.example.myscribbles.model.EntryWithImages
import com.example.myscribbles.model.Quote
import com.example.myscribbles.repository.EntryRepository
import com.example.myscribbles.repository.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScribbleViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val entryRepository: EntryRepository
) : ViewModel() {
    val failure = MutableLiveData<Boolean>()
    val quote = MutableLiveData<Quote>()
    val loading = MutableLiveData<Boolean>()
    var jobGetQuote: Job? = null

    fun getQuote(maxLength: Int) {
        loading.value = true
        jobGetQuote = CoroutineScope(Dispatchers.IO).launch {
            val quoteResponse = quotesRepository.getQuote(maxLength)
            if (quoteResponse.isSuccessful) {
                quote.postValue(quoteResponse.body())
                loading.postValue(false)
                failure.postValue(false)
            } else {
                loading.postValue(false)
                failure.postValue(true)
            }
        }
    }
    fun getAllEntries(): LiveData<List<EntryWithImages>>{
        return entryRepository.getEntries()
    }
    override fun onCleared() {
        super.onCleared()
        jobGetQuote?.cancel()
    }
}