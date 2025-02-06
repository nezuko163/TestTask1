package com.nezuko.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAll(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(vararg courses: CourseEntity)

    @Query("DELETE FROM courses")
    suspend fun deleteAll()

    @Update
    suspend fun updateCourses(vararg courses: CourseEntity)

    @Query("UPDATE courses SET is_favourite = :isFavourite WHERE course_id = :id")
    suspend fun updateIsFavourite(id: Long, isFavourite: Boolean): Int

    @Query("DELETE FROM courses WHERE course_id = :id")
    suspend fun deleteById(id: Long)
}