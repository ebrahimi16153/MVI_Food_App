package com.github.ebrahimi16153.mvifoodapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import com.github.ebrahimi16153.mvifoodapp.util.Constant.FOOD_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeal(meal: FoodList.Meal)

    @Update
    suspend fun updateMeal(meal: FoodList.Meal)

    @Delete
    suspend fun deleteMeal(meal: FoodList.Meal)

    @Query("DELETE FROM $FOOD_TABLE")
    fun deleteAll()

    @Query("SELECT * FROM $FOOD_TABLE")
    fun getAllMeal(): Flow<MutableList<FoodList.Meal>>


    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_TABLE WHERE idMeal =:id)")
    fun foodExists(id: String): Flow<Boolean>


}