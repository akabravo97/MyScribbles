package com.example.myscribbles.repository

import com.example.myscribbles.dao.EntryDao
import com.example.myscribbles.model.Entry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRepository @Inject constructor(private val entryDao: EntryDao) {
    fun getEntries() = entryDao.getAllEntries()
    fun searchEntriesByKey(key: String) = entryDao.getEntriesBySearchKey(key)
    fun deleteEntryById(id: String) = entryDao.deleteById(id)
    fun insertEntry(entry: Entry): Long = entryDao.insert(entry)
}