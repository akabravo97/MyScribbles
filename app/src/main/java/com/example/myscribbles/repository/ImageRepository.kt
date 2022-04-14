package com.example.myscribbles.repository

import com.example.myscribbles.dao.ImageDao
import com.example.myscribbles.model.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val imageDao: ImageDao) {
    fun deleteImage(image: Image) = imageDao.deleteFromImage(image)
    fun insertImage(image: Image) = imageDao.insertIntoImage(image)
}