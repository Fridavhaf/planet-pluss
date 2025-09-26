package com.example.a385353_planetpluss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.a385353_planetpluss.navigation.AppNavHost
import com.example.a385353_planetpluss.ui.theme._385353PlanetPlussTheme


/**
 * MainActivity is the entry point of the app.
 * It sets up the Compose content, applies the theme, and initializes
 * the navigation graph using a NavController.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _385353PlanetPlussTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Sets up the NavController and calls the navigation graph
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    AppNavHost(
        navController = navController
    )
}