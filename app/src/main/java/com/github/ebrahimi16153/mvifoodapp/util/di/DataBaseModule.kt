package com.github.ebrahimi16153.mvifoodapp.util.di

import android.content.Context
import androidx.room.Room
import com.github.ebrahimi16153.mvifoodapp.data.db.FoodDB
import com.github.ebrahimi16153.mvifoodapp.util.Constant.FOOD_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, FoodDB::class.java, FOOD_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideDao(database: FoodDB) = database.foodDao


}