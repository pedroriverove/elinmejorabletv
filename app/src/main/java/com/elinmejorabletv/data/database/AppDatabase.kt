package com.elinmejorabletv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elinmejorabletv.data.database.dao.TrackDao
import com.elinmejorabletv.data.database.entities.TrackEntity
import com.elinmejorabletv.data.database.utils.DateConverter

@Database(
    entities = [TrackEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}