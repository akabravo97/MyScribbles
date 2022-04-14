package com.example.myscribbles.view.scribble

import android.location.Location
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myscribbles.model.Entry
import com.example.myscribbles.model.Image
import com.example.myscribbles.repository.EntryRepository
import com.example.myscribbles.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateEntryViewModel @Inject constructor(
    val entryRepository: EntryRepository,
    val imageRepository: ImageRepository
) :
    ViewModel() {
    val selectedDate = MutableLiveData<Date>()
    var imageUri = listOf<Uri>()

    var jobInsertEntry: Job? = null

    fun updateImageList(newUri: List<Uri>) {

        imageUri = newUri
    }

    fun getSelectedTime(): Long {
        return selectedDate.value!!.time
    }

    fun updateLocation(location: Location) {
        println("Location ${location.altitude}")
    }

    fun updateDate(date: Date) {
        val c = Calendar.getInstance()
        c.set(date.year + 1900, date.month, date.date)
        selectedDate.value = c.time
    }

    fun updateDate(hour: Int, minute: Int) {
        val c = Calendar.getInstance()
        c.time = selectedDate.value
        c.set(Calendar.HOUR, hour)
        c.set(Calendar.MINUTE, minute)
        selectedDate.value = c.time
    }

    fun onDateSelected(): LiveData<Date> {
        return selectedDate
    }

    fun insertNewEntry(entry: Entry) {
        jobInsertEntry = CoroutineScope(Dispatchers.IO).launch {
            val id = entryRepository.insertEntry(entry)
            if (imageUri.size > 0) {
                for (uri in imageUri) {
                    imageRepository.insertImage(Image(0, id.toInt(), uri.toString()))
                }
            }
        }

    }
}