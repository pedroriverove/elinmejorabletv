package com.elinmejorabletv.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elinmejorabletv.data.database.entities.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Query("SELECT * FROM tracks WHERE isActive = 1 ORDER BY priority")
    fun getActiveTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE state = :region AND isActive = 1 ORDER BY priority")
    fun getActiveTracksByRegion(region: String): Flow<List<TrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<TrackEntity>)

    @Query("DELETE FROM tracks")
    suspend fun deleteAll()
}