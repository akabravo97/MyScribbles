package com.example.myscribbles.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class EntryLocation(
    @PrimaryKey val id: Int,
    val latitute: Double,
    val longitude: Double
)
