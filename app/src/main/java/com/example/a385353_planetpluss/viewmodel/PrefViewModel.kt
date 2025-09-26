package com.example.a385353_planetpluss.viewmodel

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

/**
 * PrefViewModel handles saving and loading user preferences for the game.
 * It manages the number of questions the user wants per game.
 * Preferences are stored using SharedPreferences, so they persist even
 * if the app is closed or restarted.
 */
class PrefViewModel(application: Application) : AndroidViewModel(application) {

    // Name of the SharedPreferences file
    private val prefsName = "game_prefs"

    // Key used to store the number of questions preference
    private val keyNumQuestions = "num_questions"


    /** Save number of questions to SharedPreferences */
    fun saveNumQuestions(value: Int) {
        val sharedPreferences = getApplication<Application>()
            .getSharedPreferences(prefsName, Application.MODE_PRIVATE)

        sharedPreferences.edit {
            putInt(keyNumQuestions, value)
        }
    }

    /** Load number of questions from SharedPreferences, default 5 */
    fun getNumQuestions(): Int {
        val sharedPreferences = getApplication<Application>()
            .getSharedPreferences(prefsName, Application.MODE_PRIVATE)

        return sharedPreferences.getInt(keyNumQuestions, 5)
    }
}