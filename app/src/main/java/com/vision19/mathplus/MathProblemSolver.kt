package com.vision19.mathplus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathProblemSolver(agent: MathProblemAgent, userAnswerViewModel: UserAnswerViewModel) {
    var showAnswer by remember { mutableStateOf(false) }
    var question by remember { mutableStateOf("") }
    var userResponse by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf(false) }

    if (!showAnswer) {
        question = agent.generateMathProblem() // Ask the agent to generate a math problem
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = question, style = MaterialTheme.typography.labelMedium)
        if (showAnswer) {
            Text(
                text = if (isCorrect) "Correct!" else "Incorrect!",
                color = if (isCorrect) Color.Green else Color.Red,
                style = MaterialTheme.typography.labelMedium
            )
        } else {

            TextField(
                value = userResponse,
                onValueChange = { userResponse = it },
                label = { Text("Your Answer") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(16.dp)
            )


            Button(
                onClick = {
                    // Evaluate the user's response using the agent
                    isCorrect = agent.evaluateResponse(question, userResponse)
                    showAnswer = true

                    // You can also save the user's response and correctness to a view model
                    val userAnswer = UserAnswer(
                        questionType = "math", // Adjust this based on your use case
                        question = question,
                        userAnswer = userResponse.toIntOrNull() ?: 0,
                        isCorrect = isCorrect
                    )
                    userAnswerViewModel.insertUserAnswer(userAnswer)
                }
            ) {
                Text(text = "Submit")
            }
        }
    }
}
