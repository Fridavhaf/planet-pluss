package com.example.a385353_planetpluss.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a385353_planetpluss.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
This ViewModel manages the game state and logic
It handles question selection, answer submission, scoring, and game reset
The ViewModel exposes UI state to the composable via StateFlow
 */

class GameViewModel(application: Application) : AndroidViewModel(application)
{
    // Load all questions and answers from resources
    private val allQuestions = application.resources.getStringArray(R.array.questions).toList()
    private val allAnswers = application.resources.getStringArray(R.array.answers).map { it.toInt() }

    // Load preferences for numbers of questions per round from SharedPreferences
    private val prefs = getApplication<Application>().getSharedPreferences("game_prefs", 0)
    private val numQuestions = prefs.getInt("num_questions", 5) // default 5


    // MutableStateFlow for UI state, exposing it as an immutable StateFlow
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Track which questions that have been used
    private var usedIndices = mutableSetOf<Int>()

    // Reset the game when the ViewModel is created, initializing a new game state
    init {
        resetGame()
    }

    /**
    Pick a random question that has not been used yet
    Returns pair of question text and answer Int, or null if all questions used
     */
    private fun pickRandomQuestion(): Pair<String, Int>? {
        // Available indices are all indices minus the ones already used
        val available = allQuestions.indices.toSet() - usedIndices
        if (available.isEmpty()) return null // No more questions left

        // Randomly select an index and mark it as used
        val index = available.random()
        usedIndices.add(index)

        // Return the question and its corresponding answer
        return allQuestions[index] to allAnswers[index]
    }

    /** Submit answer and update score */
    fun submitAnswer(answer: Int) {
        viewModelScope.launch {
            val state = _uiState.value

            // If the game is already finished, ignore further submissions
            if (state.finished) return@launch

            // Check if the submitted answer is correct
            val correct = state.answer
            val isCorrect = (answer == correct)

            // Update score if answer is correct
            val newScore = if (isCorrect) state.score + 1 else state.score

            // Pick the next question if there are remaining questions
            val nextQuestion = if (state.answeredCount + 1 < numQuestions) pickRandomQuestion() else null
            val finished = nextQuestion == null

            // Update the UI state with new values
            _uiState.update {
                it.copy(
                    question = nextQuestion?.first ?: it.question,
                    answer = nextQuestion?.second ?: it.answer,
                    score = newScore,
                    answeredCount = it.answeredCount + 1,
                    finished = finished,
                    lastAnswerCorrect = isCorrect,
                    lastQuestion = it.question,
                    lastCorrectAnswer = it.answer,
                    lastUserAnswer = answer.toString()
                )
            }
        }
    }

    /** Clear the last answers correctness flag */
    fun clearLastAnswer() {
        _uiState.update {
            it.copy(lastAnswerCorrect = null)
        }
    }

    /** Resets the game */
    fun resetGame() {
        usedIndices.clear()
        val first = pickRandomQuestion()
        _uiState.value = GameUiState(
            question = first?.first ?: "",
            answer = first?.second ?: 0,
            score = 0,
            answeredCount = 0,
            finished = false,
            lastAnswerCorrect = null
        )
    }
}

/** Holds game UI state */
data class GameUiState(
    val question: String = "",
    val answer: Int = 0,
    val score: Int = 0,
    val answeredCount: Int = 0,
    val finished: Boolean = false,
    val lastAnswerCorrect: Boolean? = null, // Null: No answers yet
    val lastQuestion: String? = null,
    val lastCorrectAnswer: Int? = null,
    val lastUserAnswer: String? = null
)