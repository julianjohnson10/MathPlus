package com.vision19.mathplus

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserAnswerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserAnswerRepository
    val allUserAnswers: LiveData<List<UserAnswer>>

    init {
        val userAnswersDao = AppDatabase.getDatabase(application).userAnswerDao()
        repository = UserAnswerRepository(userAnswersDao)
        allUserAnswers = repository.allUserAnswers
    }

    fun insertUserAnswer(userAnswer: UserAnswer) = viewModelScope.launch {
        repository.insert(userAnswer)
    }

    fun deleteUserAnswer(userAnswer: UserAnswer) = viewModelScope.launch {
        repository.delete(userAnswer)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    // Add other methods for update, delete as needed
}
