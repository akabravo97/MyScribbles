package com.example.myscribbles.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.myscribbles.model.Image

@Dao
interface ImageDao {

    @Insert
    fun insertIntoImage(image: Image)
    @Delete
    fun deleteFromImage(image: Image)
}