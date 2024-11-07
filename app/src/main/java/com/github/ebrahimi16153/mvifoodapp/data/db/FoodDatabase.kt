package com.github.ebrahimi16153.mvifoodapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList

@Database([FoodList.Meal::class], version = 1, exportSchema = false)
abstract class FoodDatabase:RoomDatabase() {
    abstract val foodDao: FoodDao
}