package com.example.myscribbles.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val noteId: Int,
    val title: String,
    val content: String,
    val time: Long
)
