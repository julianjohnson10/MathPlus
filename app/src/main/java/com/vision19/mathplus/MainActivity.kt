package com.vision19.mathplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vision19.mathplus.ui.theme.MathCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathCardsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                    ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = MainDestinations.START_SCREEN_ROUTE) {
                        composable(MainDestinations.START_SCREEN_ROUTE) {
                            StartScreen(navController, userAnswerViewModel = viewModel())
                        }
                        composable(
                            route = MainDestinations.FLASH_CARD_ROUTE,
                            arguments = listOf(navArgument(MainDestinations.OPERATION_ARG) { type = NavType.StringType })
                        ) { backStackEntry ->
                            val operation = backStackEntry.arguments?.getString(MainDestinations.OPERATION_ARG)

                            val userAnswerViewModel: UserAnswerViewModel = viewModel()
//                            val context = this@MainActivity
//                            val mathProblemAgent = MathProblemAgent(context, modelPath = "test.txt")
                            // Call the FlashCard composable and pass the operation and ViewModel
                            FlashCard(operation = operation ?: "addition", userAnswerViewModel = userAnswerViewModel)
//                            MathProblemSolver(agent = mathProblemAgent , userAnswerViewModel = userAnswerViewModel )
                        }
                        composable(MainDestinations.STATISTICS) {
//                            StatisticsMenu(navController, userAnswerViewModel = viewModel())
                            EnhancedStatisticsMenu(userAnswerViewModel = viewModel())
                        }
                    }
                }
            }
        }
//        // Example of how to use the connectToDatabase function with coroutines
//        val scope = MainScope() // Use a CoroutineScope appropriate for your app
//        scope.launch {
//            val connection = AzureDBConnection.connectToDatabase()
//            if (connection != null) {
//                // Database connection successful, perform your database operations here
//                // Remember to close the connection when done
//
//
//
//                connection.close()
//            } else {
//                // Handle connection error
//                // You can display an error message or take appropriate action
//            }
//        }
    }
}

// Assuming you have your FlashCard composable defined somewhere, similar to what we discussed earlier.

object MainDestinations {
    const val START_SCREEN_ROUTE = "StartScreen"
    const val FLASH_CARD_ROUTE = "FlashCard/{operation}"
    const val OPERATION_ARG = "operation"
    const val STATISTICS = "statisticsMenu"
}
