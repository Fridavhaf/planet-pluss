package com.example.a385353_planetpluss.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.a385353_planetpluss.R
import com.example.a385353_planetpluss.ui.theme.BrightPink
import com.example.a385353_planetpluss.ui.theme.LightYellow

/**
A composable dialog used to provide feedback to the user after each question,
and at the end of the game.

It can show:
1. Correct answer feedback
2. Wrong answer feedback
3. End-of-game summary with total score

The dialog adapts its content (title, message, and buttons) depending on the context.
 */

@Composable
fun FeedbackDialog(
    isAnswerCorrect: Boolean? = null, // true = correct, false = wrong, null = end-of-game
    questionText: String? = null,             // eks: "10 + 8"
    correctAnswer: Int? = null,               // eks: 18
    userAnswer: String? = null,               // eks: "3"
    score: Int? = null, // Total score, used only in end-of-game dialog
    totalQuestions: Int? = null, // Total questions, used only in end-of-game dialog
    onDismiss: () -> Unit, // Called when user dismisses a feedback dialog after a question
    onPlayAgain: (() -> Unit)? = null, // Called when user chooses to play again at end-of-game
    showExitButton: Boolean = false, // If true, an "Exit" button is displayed (end-of-game)
    onExit: (() -> Unit)? = null // Called when user chooses to exit the game
) {
    // Determine the title depending on the answer status
    val title = when {
        isAnswerCorrect != null -> if (isAnswerCorrect)
            stringResource(R.string.correct_answer_title)
        else
            stringResource(R.string.wrong_answer_title)

        score == 0 -> stringResource(R.string.finished_game_neutral) // if all answers ar wrong
        else -> stringResource(R.string.finished_game_congrats)
    }


    // Determine the text depending on the answer status
    val text = when (isAnswerCorrect) {
        true -> stringResource(R.string.correct_feedback, questionText ?: "", correctAnswer ?: 0)
        false -> stringResource(
            R.string.wrong_feedback,
            userAnswer ?: "?",
            questionText ?: "",
            correctAnswer ?: 0
        )
        null -> stringResource(R.string.feedback_message, score ?: 0, totalQuestions ?: 0) // End-of-game summary
    }

    AlertDialog(
        // Called when the user taps outside the dialog or presses the system back button
        onDismissRequest = {
            if (isAnswerCorrect == null) {
                // If end-of-game dialog, dismissing triggers exit
                onExit?.invoke()
            } else {
                // Otherwise, just close the feedback for this question
                onDismiss()
            }
        },
        title = { Text(
            title,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
            )
        }, // Display the dialog title
        text = { Text(
            text,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
            )
        },   // Display the dialog text

        // Main action button
        confirmButton = {
            TextButton(
                onClick = {
                    if (isAnswerCorrect == null) {
                        // End-of-game: "Play again" button pressed
                        onPlayAgain?.invoke()
                    } else {
                        // Question feedback: "OK"/"Next" button pressed
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrightPink,
                    contentColor = LightYellow
            )
            ) {
                // Button text changes depending on the context
                Text(
                    if (isAnswerCorrect == null) stringResource(R.string.play_again)
                    else stringResource(R.string.next_button),
                    fontSize = 25.sp
                )
            }
        },

        dismissButton = {
            if (showExitButton) {
                TextButton(onClick = { onExit?.invoke() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightYellow,
                        contentColor = BrightPink
                    )
                    ) {
                    Text(stringResource(R.string.exit_game_title),
                        fontSize = 25.sp
                    )
                }
            }
        }
    )
}