package com.vision19.mathplus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
    var backgroundColor = Color.Transparent
    var choice by remember { mutableIntStateOf(0)}
    Box(
        modifier = Modifier.fillMaxSize().background(backgroundColor) // Set the background color here
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            // Create references for the composables to constrain
            val (questionText, answerText, checkMark, answerChoices) = createRefs()

            val answer = if (showAnswer) {
                "$num1 ${getOperationSymbol(operation)} $num2 = $choice"
            } else {
                "$num1 ${getOperationSymbol(operation)} $num2 ="
            }
            Text(
                text = answer,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.constrainAs(questionText) {
                    top.linkTo(parent.top, margin = 60.dp)
                    //                bottom.linkTo(answerText.top, margin = 10.dp)
                    centerHorizontallyTo(parent)
                },
                textAlign = TextAlign.Center
            )

            if (countdownSeconds > 0) {
                // Update 'correct' based on the user's choice and the correct answer
//                val userChoice = choices.find { it == result }
//                correct = userChoice != null && userChoice == result
                backgroundColor = Color.Red
                Text(
                    text = if (correct) "Correct!" else "Incorrect!",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (correct) Color.Green else Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(answerText) {
                        top.linkTo(questionText.bottom)
                        //                    bottom.linkTo(checkMark.top)
                        centerHorizontallyTo(parent)
                    },
                )
                // Add a green or red checkmark if correct or incorrect
                Text(
                    text = if (correct) "✓" else "✗",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (correct) Color.Green else Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(checkMark) {
                        top.linkTo(answerText.top)
                        bottom.linkTo(answerChoices.top)
                        centerHorizontallyTo(parent)
                    },
                )
            }

            Box(
                modifier = Modifier.constrainAs(answerChoices) {
                    //                top.linkTo(checkMark.bottom) // Always below answerText
                    bottom.linkTo(parent.bottom, margin = 50.dp)
                    centerHorizontallyTo(parent)
                }
            ) {
                // Replace your Row with the FourButtonGrid
                MultipleChoices(
                    choices = choices,
                    onClick = { selectedChoice ->

                        // Update 'correct' based on the user's choice and the correct answer
                        correct = selectedChoice == result
                        choice = selectedChoice
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