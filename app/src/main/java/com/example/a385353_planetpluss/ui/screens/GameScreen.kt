package com.example.a385353_planetpluss.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.a385353_planetpluss.viewmodel.GameViewModel
import com.example.a385353_planetpluss.R
import com.example.a385353_planetpluss.ui.components.ExitGameDialog
import com.example.a385353_planetpluss.ui.components.FeedbackDialog
import com.example.a385353_planetpluss.ui.theme.BrightPink
import com.example.a385353_planetpluss.ui.theme.DarkBlueBackground
import com.example.a385353_planetpluss.ui.theme.LightYellow
import com.example.a385353_planetpluss.ui.theme.White
import androidx.compose.material.icons.filled.ChevronLeft
import com.example.a385353_planetpluss.viewmodel.PrefViewModel

/**
This composable represents the main game screen of the Math Game.
It displays the current math question, handles user input and provides feedback on submitted answers.
 */
@Composable
fun GameScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel = viewModel(),
    prefViewModel: PrefViewModel = viewModel()
) {
    // Collect the current UI state from the ViewModel as a Compose state
    val uiState by gameViewModel.uiState.collectAsState()
    val totalQuestions = prefViewModel.getNumQuestions()

    // Holds the current user input in the text field
    var userAnswer by remember { mutableStateOf(TextFieldValue("")) }


    // States to show/hide dialogs
    var showExitDialog by remember { mutableStateOf(false) }
    var showAnswerFeedbackDialog by remember { mutableStateOf<Boolean?>(null) }
    var showEndGameDialog by remember { mutableStateOf(false) }


    // When the last answer in the UI state changes, show the feedback dialog
    // - uiState.lastAnswerCorrect is a nullable Boolean:
    //     true  -> last answer was correct
    //     false -> last answer was wrong
    //     null  -> no answer submitted yet
    // - The ?.let { ... } ensures we only show feedback if an answer was actually submitted
    // - The Boolean value (true/false) is passed to showAnswerFeedbackDialog
    //   and then to FeedbackDialog to display appropriate feedback (correct or wrong)

    LaunchedEffect(uiState.lastAnswerCorrect) {
        uiState.lastAnswerCorrect?.let { showAnswerFeedbackDialog = it }
    }

    // When the game is finished AND no feedback is being shown, show the end-game dialog
    LaunchedEffect(uiState.finished, showAnswerFeedbackDialog) {
        if (uiState.finished && showAnswerFeedbackDialog == null) showEndGameDialog = true
    }

    // Root container for all UI elements
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(DarkBlueBackground)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ){

            Image(
                painter = painterResource(id = R.drawable.mappe1_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(15.dp))

            val currentQuestionNumber = if (uiState.finished) totalQuestions else uiState.answeredCount + 1
            Text(
                text = stringResource(
                    R.string.question_progress,
                    currentQuestionNumber,
                    totalQuestions
                ),
                fontSize = 25.sp,
                color = White
            )

            Spacer(modifier = Modifier.height(15.dp))


            // Displays the current math question and input field in the same row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.question,
                    fontSize = 50.sp,
                    color = LightYellow,
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 50.sp,
                        color = LightYellow
                    ),
                    modifier = Modifier
                        .width(115.dp)
                        .border(
                            width = 3.dp,
                            color = LightYellow,
                            shape = RoundedCornerShape(12.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Numeric keypad
            val numbers = listOf(
                listOf("1","2","3"),
                listOf("4","5","6"),
                listOf("7","8","9"),
                listOf("0")
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                numbers.forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        row.forEach { num ->
                            Button(
                                onClick = { userAnswer = TextFieldValue(userAnswer.text + num) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrightPink,
                                    contentColor = LightYellow
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(80.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(num, fontSize = 30.sp)
                            }
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Bottom row with "Delete" and "Submit" buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { userAnswer = TextFieldValue(userAnswer.text.dropLast(1)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrightPink,
                        contentColor = LightYellow
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Backspace,
                        contentDescription = "Delete",
                        modifier = Modifier.size(50.dp)
                    )
                }

                Button(
                    onClick = {
                        val answerInt = userAnswer.text.toIntOrNull()
                        if (answerInt != null) {
                            gameViewModel.submitAnswer(answerInt)
                            userAnswer = TextFieldValue("")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =LightYellow,
                        contentColor = BrightPink
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .height(80.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = stringResource(R.string.submit), fontSize = 30.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        IconButton(
            onClick = { showExitDialog = true },
            modifier = Modifier
                .padding(top = 54.dp, start = 8.dp)
                .size(65.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Back",
                tint = LightYellow,
                modifier = Modifier.size(65.dp)
            )
        }


        // Show exit confirmation dialog if user pressed back button
        if (showExitDialog) {
            ExitGameDialog(
                onConfirmExit = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onDismiss = { showExitDialog = false }
            )
        }

        // Show feedback dialog for the last submitted answer
        showAnswerFeedbackDialog?.let { isCorrect ->
            FeedbackDialog(
                isAnswerCorrect = isCorrect,
                questionText = uiState.lastQuestion,
                correctAnswer = uiState.lastCorrectAnswer,
                userAnswer = uiState.lastUserAnswer,
                onDismiss = {
                    showAnswerFeedbackDialog = null
                    gameViewModel.clearLastAnswer()
                }
            )
        }

        // Show end-of-game dialog when game finishes
        if (showEndGameDialog) {
            FeedbackDialog(
                isAnswerCorrect = null,
                score = uiState.score,
                totalQuestions = uiState.answeredCount,
                showExitButton = true,
                onDismiss = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onPlayAgain = {
                    gameViewModel.resetGame() // Reset game state
                    showEndGameDialog = false
                    showAnswerFeedbackDialog = null
                },
                onExit = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }
            )
        }
    }
}