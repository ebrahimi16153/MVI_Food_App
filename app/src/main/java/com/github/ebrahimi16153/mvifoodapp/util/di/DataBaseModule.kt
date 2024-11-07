package com.github.ebrahimi16153.foodapp.util.di

import android.content.Context
import androidx.room.Room
import com.github.ebrahimi16153.mvifoodapp.data.db.FoodDatabase
import com.github.ebrahimi16153.mvifoodapp.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, FoodDatabase::class.java, Constant.FOOD_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(database: FoodDatabase) = database.foodDao


}