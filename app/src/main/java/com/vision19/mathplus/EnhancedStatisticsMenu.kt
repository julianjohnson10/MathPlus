package com.vision19.mathplus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnhancedStatisticsMenu(userAnswerViewModel: UserAnswerViewModel) {
    val userAnswers: List<UserAnswer>? = userAnswerViewModel.allUserAnswers.observeAsState().value
    val totalQuestions = userAnswers?.size ?: 0
    val correctAnswers = userAnswers?.count { it.isCorrect } ?: 0
    val wrongAnswers = userAnswers?.count { !it.isCorrect } ?: 0

    // Use a responsive layout
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Statistics Report",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Display overall stats with graphical representation
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatCard("Correct", correctAnswers)
                StatCard("Incorrect", wrongAnswers)
                StatCard("Total", totalQuestions)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Detailed stats with charts
            DetailedStatisticsSection("Addition", userAnswers, "addition")
            DetailedStatisticsSection("Subtraction", userAnswers, "subtraction")
            DetailedStatisticsSection("Multiplication", userAnswers, "multiplication")

            Spacer(modifier = Modifier.height(40.dp))
            // Clear statistics button
            Button(
                onClick = { userAnswerViewModel.deleteAll() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Clear Statistics", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun DetailedStatisticsSection(title: String, userAnswers: List<UserAnswer>?, questionType: String) {
    val questions = userAnswers?.filter { it.questionType == questionType }
    val correct = questions?.count { it.isCorrect } ?: 0
    val wrong = questions?.count { !it.isCorrect } ?: 0

    Text(text = title, style = MaterialTheme.typography.labelSmall)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        StatCard("Correct", correct)
        StatCard("Incorrect", wrong)
        StatCard("Total", questions?.size ?: 0)
    }

    // Placeholder for chart
    // Implement a chart view here, e.g., a bar chart or pie chart
}

@Composable
fun StatCard(label: String, value: Int) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value.toString(), style = MaterialTheme.typography.labelMedium)
            Text(text = label, style = MaterialTheme.typography.labelSmall)
        }
    }
}
