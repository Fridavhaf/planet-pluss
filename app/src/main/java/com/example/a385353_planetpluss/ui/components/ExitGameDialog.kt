package com.example.a385353_planetpluss.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a385353_planetpluss.R
import com.example.a385353_planetpluss.ui.theme.BrightPink
import com.example.a385353_planetpluss.ui.theme.LightYellow


/**
A composable that shows a pop up dialog if the user tries to exit the game
User can then choose yes or no.
To end the game is an important choice, so we want to double check with the user
 */


@Composable
fun ExitGameDialog(
    onConfirmExit: () -> Unit, // If user press yes, onConfirmExit is called
    onDismiss: () -> Unit // If user press no, onDismiss is called
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.exit_game_title),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = stringResource(R.string.exit_game_text),
                fontSize = 28.sp,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = onConfirmExit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrightPink,
                        contentColor = LightYellow
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = stringResource(R.string.yes), fontSize = 28.sp)
                }
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightYellow,
                        contentColor = BrightPink
                    )
                ) {
                    Text(text = stringResource(R.string.no), fontSize = 28.sp)
                }
            }
        }
    )
}