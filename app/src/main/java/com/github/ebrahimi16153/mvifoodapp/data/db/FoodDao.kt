package com.github.ebrahimi16153.mvifoodapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import com.github.ebrahimi16153.mvifoodapp.util.Constant
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable


@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeal(meal: FoodList.Meal):Completable

    @Update
    fun updateMeal(meal: FoodList.Meal):Completable

    @Delete
    fun deleteMeal(meal: FoodList.Meal):Completable

    @Query("DELETE FROM ${Constant.FOOD_TABLE}")
    fun deleteAll()

    @Query("SELECT * FROM ${Constant.FOOD_TABLE}")
    fun getAllMeal():Observable<List<FoodList.Meal>>


    @Query("SELECT EXISTS (SELECT 1 FROM ${Constant.FOOD_TABLE} WHERE idMeal =:id)")
    fun foodExists(id:Int):Observable<Boolean>


}