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
    private var chartDataBase: ChartDataBase? = null
/*
    */
    /**
     * from developers android, made my own singleton
     *
     * @param context
     * @return
     *//*
    fun getInstance(context: Context): ChartDataBase {
        if (chartDataBase == null) {
            chartDataBase = Room.databaseBuilder(
                context.applicationContext,
                ChartDataBase::class.java, "database-name"
            ).build()
        }
        return chartDataBase as ChartDataBase
    }*/


    //abstract fun thirtyDayDao(): ThirtyWeekDAO

    abstract fun fiveWeekDao(): FiveWeekDAO
    //abstract fun tenWeekDao(): TenWeekDAO

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