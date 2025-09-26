package com.example.a385353_planetpluss.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a385353_planetpluss.R
import com.example.a385353_planetpluss.ui.theme.BrightPink
import com.example.a385353_planetpluss.ui.theme.DarkBlueBackground
import com.example.a385353_planetpluss.ui.theme.LightYellow
import com.example.a385353_planetpluss.ui.theme.White
import com.example.a385353_planetpluss.ui.theme.Yellow

/**
HomeScreen: main start screen with app title and navigation buttons.
Uses navController to navigate to GameScreen, AboutScreen, or PreferencesScreen.
Modifier allows layout adjustments.
 */
@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {



    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(DarkBlueBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painterResource(id = R.drawable.mappe1_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(15.dp))


        Text(
            text = stringResource(R.string.welcome),
            color = White,
            fontSize = 30.sp
        )

        // App title
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 35.sp,
            color = White
        )

        Spacer(modifier = Modifier.height(35.dp))

        // Start game button
        Button(onClick = { navController.navigate("game") },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrightPink,
                contentColor = LightYellow
            )
        ) {
            Text(text = stringResource(R.string.start_game),
                fontSize = 40.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // About game button
        Button(onClick = { navController.navigate("about") },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrightPink,
                contentColor = LightYellow
            )
        ) {
            Text(text = stringResource(R.string.about_game),
                fontSize = 40.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Preferences button
        Button(onClick = { navController.navigate("preferences") },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrightPink,
                contentColor = LightYellow
            )
        ) {
            Text(text = stringResource(R.string.preferences),
                fontSize = 40.sp
            )
        }
    }
}