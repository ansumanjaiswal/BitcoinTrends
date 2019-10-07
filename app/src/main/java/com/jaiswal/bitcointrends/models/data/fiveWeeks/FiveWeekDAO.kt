package com.jaiswal.bitcointrends.models.data.fiveWeeks

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FiveWeekDAO {
    @Query("SELECT * FROM 'fiveweekchartpoint'")
    fun getAll(): LiveData<List<FiveWeekChartPoint>>

    @Query("SELECT * FROM 'fiveweekchartpoint' WHERE id LIKE :id")
    fun findByTitle(id: String): FiveWeekChartPoint

    @Insert
    fun insertAll(vararg data: FiveWeekChartPoint)

    @Delete
    fun delete(data: FiveWeekChartPoint)

    @Update
    fun updateTodo(vararg data: FiveWeekChartPoint)
}