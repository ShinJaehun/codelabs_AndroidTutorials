package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "GameViewModel"

class GameViewModel : ViewModel() {

    private var wordsList: MutableList<String> = mutableListOf()
    // 헐~ 이게 init{} 보다 밑에 있으면 40 행에서 wordsList: null object reference 발생
    // https://github.com/google-developer-training/android-basics-kotlin-unscramble-app/issues/19

//    private lateinit var _currentScrambledWord: String
//    val currentScrambledWord: String
//        get() = _currentScrambledWord

    private val _currentScrambledWord = MutableLiveData<String>()
    // 같은 원리로 씨발 이 새끼도 init{} 보다 밑에 있으면 null object refernece 발생
    // object의 value는 동일하게 유지되고 object에 저장된 데이터만 변경됨!
    // 그래서 var가 아니라 val로 바뀌어야 한데...
    val currentScrambledWord: LiveData<String> // 지원필드 currentScrambledWord는 변경불가이기 때문에 LiveData<String>
        get() = _currentScrambledWord

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    private fun getNextWord() { // 근데 왜 얘가 두번 호출되는 걸까?
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            // shuffle() 했는데 순서를 바꾸지 않은 원래 단어와 동일하면 한번 더 하시구요...
            tempWord.shuffle()
        }

//        Log.i(TAG, "currentWord: $currentWord")
//        Log.i(TAG, "tempWord: ${String(tempWord)}")

        if (wordsList.contains(currentWord)) { // 계속 여기서 null object reference... 발생
            // 이미 사용했던 단어라면
            getNextWord()
        } else {
//            _currentScrambledWord = String(tempWord)
            _currentScrambledWord.value = String(tempWord)
            // LiveData object 내 데이터에 access하려면 value
//            ++_currentWordCount
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }

//        wordsList.forEachIndexed { i, word ->
//            Log.i(TAG, "wordsList[$i]: $word")
//        }
    }

    private fun increaseScore() {
//        _score += SCORE_INCREASE
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord: String) : Boolean {
        return if (playerWord.equals(currentWord, true)) {
            increaseScore()
            true
        } else false
    }

    fun nextWord(): Boolean {
//        return if (currentWordCount < MAX_NO_OF_WORDS) {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

//    override fun onCleared() {
//        Log.d("GameFragment", "GameViewModel destroyed!")
//        super.onCleared()
//    }
}

