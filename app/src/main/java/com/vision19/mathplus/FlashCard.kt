package com.vision19.mathplus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun FlashCard(operation: String, userAnswerViewModel: UserAnswerViewModel) {
    var num1 by remember { mutableIntStateOf(Random.nextInt(10)) }
    var num2 by remember { mutableIntStateOf(Random.nextInt(10)) }
    var showAnswer by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf(calculateResult(num1, num2, operation)) }
    var correct by remember { mutableStateOf(false) }
    var choices by remember { mutableStateOf(generateChoices(result, operation)) }
    var countdownSeconds by remember { mutableIntStateOf(0) }
    val question = "$num1 ${getOperationSymbol(operation)} $num2"
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Reserve space for the correct answer text
        if (showAnswer) {
            Text(
                text = "$num1 ${getOperationSymbol(operation)} $num2 = $result",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 30.dp) // Adjust the padding as needed
            )
        } else {
            Text(
                text = "$num1 ${getOperationSymbol(operation)} $num2 =",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        // Display the countdown timer
        // If the countdown timer is greater than 0, display the correct or incorrect message
        if (countdownSeconds > 0) {
            Text(
                text = if (correct) "Correct!" else "Incorrect!",
                style = MaterialTheme.typography.labelMedium,
                color = if (correct) Color.Green else Color.Red
            )
            //Add a green or red checkmark if correct or incorrect

            if (correct) {
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Green
                )
            } else {
                Text(
                    text = "✗",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Red
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp) // Add padding if needed
                .width(400.dp) // Set the size of the MultipleChoices composable
                .offset(y = 16.dp), // Offset from the top
            contentAlignment = Alignment.Center,
        ) {
            // Replace your Row with the FourButtonGrid
            MultipleChoices(
                choices = choices,
                onClick = { selectedChoice ->

                    correct = selectedChoice == result

                    val userAnswer = UserAnswer(
                        questionType = operation,
                        question = question,
                        userAnswer = selectedChoice,
                        isCorrect = correct
                    )


                    showAnswer = true // Show the correct answer
                    countdownSeconds = 1 // Start the countdown timer

                    coroutineScope.launch {
                        while (countdownSeconds > 0) {
                            delay(1000) // Wait for 1 second
                            countdownSeconds-- // Decrement the countdown timer
                        }
                        num1 = Random.nextInt(10)
                        num2 = Random.nextInt(10)
                        showAnswer = false
                        result = calculateResult(num1, num2, operation)
                        choices = generateChoices(result, operation)
                    }

                    userAnswerViewModel.insertUserAnswer(userAnswer)

                }
            )
        }
    }
}

fun calculateResult(num1: Int, num2: Int, operation: String): Comparable<*> {
    return when(operation) {
        "addition" -> num1 + num2
        "subtraction" -> num1 - num2
        "multiplication" -> num1 * num2
        "division" -> if (num2 != 0) num1 / num2 else "Undefined"
        else -> num1 + num2
    }
}

fun generateChoices(result: Comparable<Nothing>, operation: String): List<Int> {
    val choices = mutableListOf<Int>().apply {
        add(result as Int)
        addAll(generateRandomChoices(result, operation))
        shuffle()
    }
    return choices
}

fun generateRandomChoices(result: Comparable<Nothing>, operation: String): List<Int> {
    val choices = mutableListOf<Int>()
    while (choices.size < 3) {
        val num1 = Random.nextInt(10)
        val num2 = Random.nextInt(10)
        val randomChoice = when (operation) {
            "addition" -> num1 + num2
            "subtraction" -> num1 - num2
            "multiplication" -> num1 * num2
            "division" -> if (num2 != 0) num1 / num2 else 0
            else -> num1 + num2
        }
        if (randomChoice != result && randomChoice !in choices) {
            choices.add(randomChoice)
        }
    }
    choices.shuffle()
    return choices
}


@Composable
fun getOperationSymbol(operation: String): String {
    return when(operation) {
        "addition" -> "+"
        "subtraction" -> "-"
        "multiplication" -> "×"
        "division" -> "÷"
        else -> "+"
    }
}