package com.example.myscribbles.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.myscribbles.model.Entry
import com.example.myscribbles.model.EntryWithImages

@Dao
interface EntryDao {
    @Transaction
    @Query("SELECT * FROM entries ORDER BY time DESC")
    fun getAllEntries(): LiveData<List<EntryWithImages>>

    @Query("SELECT * FROM entries WHERE content LIKE '%' || :key || '%' OR title LIKE '%:key%'")
    fun getEntriesBySearchKey(key: String): LiveData<List<Entry>>

    @Insert(onConflict = REPLACE)
    fun insert(entry: Entry): Long

    @Query("DELETE FROM entries WHERE id = :id")
    fun deleteById(id: String)
}