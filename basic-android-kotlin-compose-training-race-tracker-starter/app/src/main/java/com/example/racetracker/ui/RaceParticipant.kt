/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.racetracker.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*

/**
 * This class represents a state holder for race participant.
 */
class RaceParticipant(
    val name: String,
    val maxProgress: Int = 100,
    val progressDelayMillis: Long = 500L,
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0
) {

    init {
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }

    /**
     * Indicates the race participant's current progress
     */
    var currentProgress by mutableStateOf(initialProgress)
        private set

//    suspend fun run() {
//        while (currentProgress < maxProgress) {
//            delay(progressDelayMillis)
//            currentProgress += progressIncrement
//            Log.d("RaceStatus", "currentProgress : $currentProgress")
//        }
//    }

    // Reset을 클릭할 때 coroutine 취소
    suspend fun run() {
        try {
            while (currentProgress < maxProgress) {
                delay(progressDelayMillis) // 이게
                currentProgress += progressIncrement
            }
        } catch (e: CancellationException) {
            Log.e("RaceStatus", "$name: ${e.message}")
            throw  e // alway re throw CancellationException
        }

    }

//     first coroutine
    fun main() = runBlocking { // this: CoroutineScope
        launch { // launch a new coroutine and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            Log.d("RaceStatus", "world!") // print after delay
        }
        Log.d("RaceStatus", "hello!") // main coroutine continues while a previous one is delayed
    }
//
//     extract function refactoring
//    fun main() = runBlocking { // this: CoroutineScope
//        launch { doWorld() }
//        Log.d("RaceStatus", "hello!")
//    }
//
//    // this is your first suspending function
//    suspend fun doWorld() {
//        delay(1000L)
//        Log.d("RaceStatus", "world!")
//    }
//
//     scope builder
//    fun main() = runBlocking {
//        doWorld()
//    }
//
//    suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
//        launch {
//            delay(1000L)
//            Log.d("RaceStatus", "world!")
//        }
//        Log.d("RaceStatus", "hello!")
//    }
//
//     scope builder and concurrency
//    // Sequentially executes doWorld followed by "Done"
//    fun main() = runBlocking {
//        doWorld()
//        Log.d("RaceStatus", "Done!")
//    }
//
//    // Concurrently executes both sections
//    suspend fun doWorld() = coroutineScope { // this: CoroutineScope
//        launch {
//            delay(2000L)
//            Log.d("RaceStatus", "world2")
//        }
//        launch {
//            delay(1000L)
//            Log.d("RaceStatus", "world1")
//        }
//        Log.d("RaceStatus", "hello!")
//    }
//
//     an explicit job
//    fun main() = runBlocking {
//        val job = launch { // launch a new coroutine and keep a reference to its Job
//            delay(1000L)
//            Log.d("RaceStatus", "world")
//        }
//        Log.d("RaceStatus", "hello")
//        job.join() // wait until child coroutine completes
//        Log.d("RaceStatus", "done")
//    }

    /**
     * Regardless of the value of [initialProgress] the reset function will reset the
     * [currentProgress] to 0
     */
    fun reset() {
        currentProgress = 0
    }
}

/**
 * The Linear progress indicator expects progress value in the range of 0-1. This property
 * calculate the progress factor to satisfy the indicator requirements.
 */
val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()
