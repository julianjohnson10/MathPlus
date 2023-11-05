package com.vision19.mathplus

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_answers")
data class UserAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionType: String,
    val question: String,
    val userAnswer: Int,
    val isCorrect: Boolean
)
