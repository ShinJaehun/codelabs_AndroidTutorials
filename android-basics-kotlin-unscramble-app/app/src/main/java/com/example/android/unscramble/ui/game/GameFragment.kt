/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    // 앱에서 다음과 같이 기본 GameViewModel 생성자를 사용하여 뷰 모델을 초기화하는 경우

    // private val viewModel = GameViewModel()

    // 기기에서 구성이 변경되는 동안 앱이 viewModel 참조의 상태를 손실하게 됩니다.
    //
    // 예를 들어 기기를 회전하면 활동이 소멸된 후 다시 생성되고 초기 상태의 새로운 뷰 모델 인스턴스가 다시 시작됩니다.
    // 대신 속성 위임 접근 방식을 사용해 viewModel 객체의 책임을 viewModels라는 별도의 클래스에 위임합니다.
    // 즉, viewModel 객체에 액세스하면 이 객체는 대리자 클래스 viewModels에 의해 내부적으로 처리됩니다.
    // 대리자 클래스는 첫 번째 액세스 시 자동으로 viewModel 객체를 만들고 이 값을 구성 변경 중에도 유지했다가 요청이 있을 때 반환합니다.

    // moved to ViewModel
//    private var score = 0
//    private var currentWordCount = 0
//    private var currentScrambledWord = "test"


    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
//        binding = GameFragmentBinding.inflate(inflater, container, false)

        // databinding
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS

        Log.d("GameFragment", "GameFragment created/re-created!")
        return binding.root
    }

//    override fun onDetach() {
//        super.onDetach()
//        Log.d("GameFragment", "GameFragment destroyed!")
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
//        updateNextWordOnScreen()

//        binding.score.text = getString(R.string.score, 0)
//        binding.wordCount.text = getString(
//                R.string.word_count, 0, MAX_NO_OF_WORDS)

        // databinding으로 더 이상 코드 필요 없음
//        viewModel.currentScrambledWord.observe(viewLifecycleOwner, { newWord ->
//            binding.textViewUnscrambledWord.text = newWord
//        })

//        viewModel.score.observe(viewLifecycleOwner, { newScore ->
//            binding.score.text = getString(R.string.score, newScore)
//        })
//
//        viewModel.currentWordCount.observe(viewLifecycleOwner, { newWordCount ->
//            binding.wordCount.text = getString(R.string.word_count, newWordCount, MAX_NO_OF_WORDS)
//        })
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    private fun onSubmitWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        score += SCORE_INCREASE
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        binding.score.text = getString(R.string.score, score)
//        setErrorTextField(false)
//        updateNextWordOnScreen()

        val playerWord = binding.textInputEditText.text.toString()

//        if (viewModel.isUserWordCorrect(playerWord)) {
//            setErrorTextField(false)
//            if (viewModel.nextWord()) {
//                updateNextWordOnScreen()
//            } else {
//                showFinalScoreDialog()
//            }
//        } else {
//            setErrorTextField(true)
//        }

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            } // LiveData를 observe하므로 updateNextWordOnScreen() 불필요
        } else {
            setErrorTextField(true)
        }

    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        setErrorTextField(false)
//        updateNextWordOnScreen()

        if (viewModel.nextWord()) {
            setErrorTextField(false)
//            updateNextWordOnScreen()
        } else {
            showFinalScoreDialog()
        }
    }

    // ViewModel 내에서 구현
    /*
     * Gets a random word for the list of words and shuffles the letters in it.
     */
//    private fun getNextScrambledWord(): String {
//        val tempWord = allWordsList.random().toCharArray()
//        tempWord.shuffle()
//        return String(tempWord)
//    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
//            .setMessage(getString(R.string.you_scored, viewModel.score))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
//        updateNextWordOnScreen()
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * Displays the next scrambled word on screen.
     */
//    private fun updateNextWordOnScreen() {
////        binding.textViewUnscrambledWord.text = currentScrambledWord
//        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
//    }
}
