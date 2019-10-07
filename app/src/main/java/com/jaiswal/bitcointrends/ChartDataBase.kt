package com.jaiswal.bitcointrends

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekChartPoint
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekDAO

//@Database(entities = [ThirtyWeekChartPoint::class, FiveWeekChartPoint::class, TenWeekChartPoint::class], version = 1)
/*@Database(
    entities = [ThirtyWeekChartPoint::class, FiveWeekChartPoint::class, TenWeekChartPoint::class],
    version = 1
)*/
@Database(
    entities = [FiveWeekChartPoint::class],
    version = 1
)
abstract class ChartDataBase : RoomDatabase() {

    abstract fun fiveWeekDao(): FiveWeekDAO

    companion object {
        @Volatile
        private var instance: ChartDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ChartDataBase::class.java, "chart-data-point.db"
        )
            .build()
    }
}