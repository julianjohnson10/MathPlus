package com.vision19.mathplus

import androidx.lifecycle.LiveData

class UserAnswerRepository(private val userAnswerDao: UserAnswerDao) {

    val allUserAnswers: LiveData<List<UserAnswer>> = userAnswerDao.getAll()

    suspend fun insert(userAnswer: UserAnswer) {
        userAnswerDao.insert(userAnswer)
    }

    suspend fun delete(userAnswer: UserAnswer) {
        userAnswerDao.delete(userAnswer)
    }

    suspend fun deleteAll() {
        userAnswerDao.deleteAll()
    }
    // Add more repository methods as needed...
}
