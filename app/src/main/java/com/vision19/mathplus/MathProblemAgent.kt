package com.vision19.mathplus

import android.content.Context
import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.random.Random

class MathProblemAgent(private val context: Context, private val modelPath: String) {
    private val interpreter: Interpreter = Interpreter(loadModelFile(modelPath))

    // Function to load the model file from the assets folder
    private fun loadModelFile(modelPath: String): ByteBuffer {
        val assetManager: AssetManager = context.assets
        val inputStream = assetManager.open(modelPath)
        val modelBytes = ByteArray(inputStream.available())
        inputStream.read(modelBytes)
        val buffer = ByteBuffer.allocateDirect(modelBytes.size)
        buffer.order(ByteOrder.nativeOrder())
        buffer.put(modelBytes)
        buffer.rewind()
        return buffer
    }

    // Function to generate a math problem
    fun generateMathProblem(): String {
        val num1 = Random.nextInt(10)
        val num2 = Random.nextInt(10)
        val operation = "addition" // Modify based on your requirements
        return "$num1 + $num2"
    }

    // Function to evaluate a user's response to a math problem
    fun evaluateResponse(problem: String, userResponse: String): Boolean {
        val parts = problem.split("+")
        val num1 = parts[0].trim().toInt()
        val num2 = parts[1].trim().toInt()
        val expectedAnswer = num1 + num2
        return userResponse.toIntOrNull() == expectedAnswer
    }
}
