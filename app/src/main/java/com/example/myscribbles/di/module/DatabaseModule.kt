package com.example.myscribbles.di.module

import android.content.Context
import com.example.myscribbles.AppDataBase
import com.example.myscribbles.dao.EntryDao
import com.example.myscribbles.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.invoke(context)
    }

    @Provides
    fun providesEntryDao(appDataBase: AppDataBase): EntryDao {
        return appDataBase.getEntryDao()
    }

    @Provides
    fun provideImageDao(appDataBase: AppDataBase): ImageDao {
        return appDataBase.getImageDao()
    }
}