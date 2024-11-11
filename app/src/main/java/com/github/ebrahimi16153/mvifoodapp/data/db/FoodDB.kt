package com.github.ebrahimi16153.mvifoodapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList

@Database(entities = [FoodList.Meal::class], version = 1, exportSchema = false)

abstract class FoodDB :RoomDatabase(){ abstract val foodDao :FoodDao }