package com.example.a385353_planetpluss.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a385353_planetpluss.R
import com.example.a385353_planetpluss.ui.theme.BrightPink
import com.example.a385353_planetpluss.ui.theme.DarkBlueBackground
import com.example.a385353_planetpluss.ui.theme.LightYellow
import com.example.a385353_planetpluss.ui.theme.White
import com.example.a385353_planetpluss.viewmodel.PrefViewModel


/**
Screen for choosing number of questions.
Options come from strings.xml and are shown with radio buttons.
The selected value is stored in SharedPreferences via PrefViewModel.
 */
@Composable
fun PrefScreen(
    navController: NavController,
    prefViewModel: PrefViewModel = viewModel()
) {
    val options = listOf(
        stringResource(R.string.num_questions_5),
        stringResource(R.string.num_questions_10),
        stringResource(R.string.num_questions_15)
    )

    var selectedOption by remember { mutableStateOf(prefViewModel.getNumQuestions().toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(DarkBlueBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painterResource(id = R.drawable.mappe1_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.num_questions_label),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp,
            color = White
        )
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable {
                        selectedOption = option
                        prefViewModel.saveNumQuestions(option.toInt())
                    }
                ) {
                    Text(option,
                        fontSize = 30.sp,
                        color = White
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    RadioButton(
                        selected = (selectedOption == option),
                        onClick = {
                            selectedOption = option
                            prefViewModel.saveNumQuestions(option.toInt())
                        },
                        modifier = Modifier.scale(1.75f), // 1.75x normal size
                        colors = RadioButtonDefaults.colors(
                            selectedColor = BrightPink,
                            unselectedColor = LightYellow,
                            disabledSelectedColor = White.copy(alpha = 0.6f),
                            disabledUnselectedColor = White.copy(alpha = 0.3f)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigateUp() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrightPink,
                contentColor = LightYellow
            )
            ) {

            Text(text = stringResource(R.string.back_button),
                fontSize = 40.sp
            )
        }
    }
}