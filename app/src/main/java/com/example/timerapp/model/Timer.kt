package com.example.timerapp.model

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class Timer(label: String, seconds: Long) {
    val name = label
    val globalTime = seconds
    val remainingTime: MutableLiveData<Long> = MutableLiveData() //in seconds
    private lateinit var timer: CountDownTimer
    var isPaused = true

    init {
        remainingTime.value = globalTime
    }

    fun startTimer() {
        try {
            isPaused = false
            timer = object : CountDownTimer(remainingTime.value?.times(1000)!!, 10) {
                override fun onTick(duration: Long) {
                    remainingTime.value = duration / 1000
                    println("$duration")
                }

                override fun onFinish() {
                    remainingTime.value = 0
                    println("$name is done!")
                }
            }.start()
        }
        catch (e : Exception) {
            println("remainingTime has not been initialised yet!")
        }
    }

    fun stopTimer() {
        try {
            isPaused = true
            timer.cancel()
        }
        catch (e: Exception) {
            println("timer has not been initialised yet!")
        }
    }
}