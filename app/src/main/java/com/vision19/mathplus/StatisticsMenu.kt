package com.vision19.mathplus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun StatisticsMenu(navController: NavController, userAnswerViewModel: UserAnswerViewModel) {
    val userAnswers: List<UserAnswer>? = userAnswerViewModel.allUserAnswers.observeAsState().value

    Box {
        Column {
            Text(text = "Statistics Menu")

            val totalQuestions = userAnswers?.size ?: 0
            Text(text = "Total Questions: $totalQuestions")
            val correctAnswers = userAnswers?.filter { it.isCorrect }?.size ?: 0
            val wrongAnswers = userAnswers?.filter { !it.isCorrect }?.size ?: 0
            Text(text = "Correct Answers: $correctAnswers")
            Text(text = "Wrong Answers: $wrongAnswers")

            Text(text = "Addition")
            val additionQuestions = userAnswers?.filter { it.questionType == "addition" }?.size ?: 0
            val additionCorrectAnswers = userAnswers?.filter { it.questionType == "addition" && it.isCorrect }?.size ?: 0
            val additionWrongAnswers = userAnswers?.filter { it.questionType == "addition" && !it.isCorrect }?.size ?: 0
            Text(text = "Total Questions: $additionQuestions")
            Text(text = "Correct Answers: $additionCorrectAnswers")
            Text(text = "Wrong Answers: $additionWrongAnswers")

            Text(text = "Subtraction")
            val subtractionQuestions = userAnswers?.filter { it.questionType == "subtraction" }?.size ?: 0
            val subtractionCorrectAnswers = userAnswers?.filter { it.questionType == "subtraction" && it.isCorrect }?.size ?: 0
            val subtractionWrongAnswers = userAnswers?.filter { it.questionType == "subtraction" && !it.isCorrect }?.size ?: 0
            Text(text = "Total Questions: $subtractionQuestions")
            Text(text = "Correct Answers: $subtractionCorrectAnswers")
            Text(text = "Wrong Answers: $subtractionWrongAnswers")
            Text(text = "Multiplication")

            //Add a button to clear the statistics
            Button(onClick = {
                userAnswerViewModel.deleteAll()
            }) {
                Text(text = "Clear Statistics")
            }

//            Text(text = "Division")


//            // Display the list of user answers
//            if (userAnswers != null && userAnswers.isNotEmpty()) {
//                LazyColumn {
//                    items(userAnswers) { answer ->/
//                        Text(text = answer.toString())  // You can modify this to display relevant info
//                    }
//                }
//            } else {
//                Text(text = "No answers available")
//            }
        }
    }
}