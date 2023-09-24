package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "GameViewModel"

class GameViewModel : ViewModel() {

    private var wordsList: MutableList<String> = mutableListOf()
    // 헐~ 이게 init{} 보다 밑에 있으면 40 행에서 wordsList: null object reference 발생
    // https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/issues/19

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    private lateinit var currentWord: String

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        Log.i(TAG, "currentWord: $currentWord")
        Log.i(TAG, "tempWord: ${String(tempWord)}")

        if (wordsList.contains(currentWord)) { // 계속 여기서 null object reference... 발생
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            _currentWordCount++
            wordsList.add(currentWord)
        }

        wordsList.forEachIndexed { i, word ->
            Log.i(TAG, "wordsList[$i]: $word")
        }
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String) : Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        } else return false
    }

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    override fun onCleared() {
        Log.d("GameFragment", "GameViewModel destroyed!")
        super.onCleared()
    }

}

