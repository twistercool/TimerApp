package com.example.timerapp.model

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class Timer(label: String, seconds: Long) {
    val name = label
    val globalTime = seconds
    val remainingTime: MutableLiveData<Long> = MutableLiveData()
    lateinit var timer: CountDownTimer

    init {
        remainingTime.value = globalTime
    }

    fun startTimer() {
        try {
            timer = object : CountDownTimer(remainingTime.value?.times(1000)!!, 10) {
                override fun onTick(duration: Long) {
                    remainingTime.value = duration
                }

                override fun onFinish() {
                    remainingTime.value = 0
                }
            }
        }
        catch (e : Exception) {
            println("remainingTime has not been initialised yet!")
        }
    }

    fun stopTimer() {
        try {
            timer.cancel()
        }
        catch (e: Exception) {
            println("timer has not been initialised yet!")
        }
    }
}