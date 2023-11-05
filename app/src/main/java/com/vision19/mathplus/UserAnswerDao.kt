package com.vision19.mathplus

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// UserAnswerDao.kt
@Dao
interface UserAnswerDao {
    @Query("SELECT * FROM user_answers")
    fun getAll(): LiveData<List<UserAnswer>>

    @Insert
    suspend fun insert(userAnswer: UserAnswer)

    // Add more queries as needed...

    @Delete
    suspend fun delete(userAnswer: UserAnswer)

    @Query("DELETE FROM user_answers")
    suspend fun deleteAll()

    @Update
    suspend fun update(userAnswer: UserAnswer)

}
