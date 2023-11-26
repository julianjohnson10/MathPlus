package com.vision19.mathplus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun StatisticsMenu(navController: NavController, userAnswerViewModel: UserAnswerViewModel) {
    val userAnswers: List<UserAnswer>? = userAnswerViewModel.allUserAnswers.observeAsState().value

    Box {
        Column {
            Text(
                text = "Statistics Menu",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                )

            val totalQuestions = userAnswers?.size ?: 0

            Text(
                text = "Total Questions: $totalQuestions",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            val correctAnswers = userAnswers?.filter { it.isCorrect }?.size ?: 0
            val wrongAnswers = userAnswers?.filter { !it.isCorrect }?.size ?: 0
            Text(
                text = "Correct Answers: $correctAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Wrong Answers: $wrongAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(text = "Addition", style = MaterialTheme.typography.labelMedium)

            val additionQuestions = userAnswers?.filter { it.questionType == "addition" }?.size ?: 0
            val additionCorrectAnswers = userAnswers?.filter { it.questionType == "addition" && it.isCorrect }?.size ?: 0
            val additionWrongAnswers = userAnswers?.filter { it.questionType == "addition" && !it.isCorrect }?.size ?: 0
            Text(
                text = "Total Questions: $additionQuestions",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Correct Answers: $additionCorrectAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Wrong Answers: $additionWrongAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(text = "Subtraction", style = MaterialTheme.typography.labelMedium)
            val subtractionQuestions = userAnswers?.filter { it.questionType == "subtraction" }?.size ?: 0
            val subtractionCorrectAnswers = userAnswers?.filter { it.questionType == "subtraction" && it.isCorrect }?.size ?: 0
            val subtractionWrongAnswers = userAnswers?.filter { it.questionType == "subtraction" && !it.isCorrect }?.size ?: 0
            Text(text = "Total Questions: $subtractionQuestions",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(text = "Correct Answers: $subtractionCorrectAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(text = "Wrong Answers: $subtractionWrongAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(text = "Multiplication", style = MaterialTheme.typography.labelMedium)

            val multiplicationQuestions = userAnswers?.filter { it.questionType == "multiplication" }?.size ?: 0
            val multiplicationCorrectAnswers = userAnswers?.filter { it.questionType == "multiplication" && it.isCorrect }?.size ?: 0
            val multiplicationWrongAnswers = userAnswers?.filter { it.questionType == "multiplication" && !it.isCorrect }?.size ?: 0

            Text(text = "Total Questions: $multiplicationQuestions",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(text = "Correct Answers: $multiplicationCorrectAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(text = "Wrong Answers: $multiplicationWrongAnswers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            //Add a button to clear the statistics
            Button(onClick = {
                userAnswerViewModel.deleteAll() // Delete all user answers
            }) {
                Text(text = "Clear Statistics",)
            }
        }
    }
}