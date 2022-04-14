package com.example.myscribbles.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val entryId: Int,
    val imagePath: String
)
