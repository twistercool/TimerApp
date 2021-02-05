package com.example.timerapp.ui.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerapp.model.Timer
import java.lang.Exception

class OverviewViewModel: ViewModel() {
    //Initialises the list with no timers
    private var timerList: MutableLiveData<MutableList<Timer>> = MutableLiveData(MutableList(0) { Timer("",0)})

    init {
        println("init OverviewViewModel")
    }

    fun getTimers(): MutableLiveData<MutableList<Timer>> {
        return timerList
    }

    fun addTimer(label: String, seconds: Long) {
        try {
            timerList.value?.add(Timer(label, seconds))
        }
        catch (e: Exception) {
            println("timerList not initialised")
        }
    }

    fun getRemainingTime(position: Int): MutableLiveData<Long>? {
        return timerList.value?.get(position)?.remainingTime
    }

    fun getOriginalTime(position: Int): Long? {
        return timerList.value?.get(position)?.globalTime
    }

    fun isTimerPaused(position: Int): Boolean? {
        return timerList.value?.get(position)?.isPaused
    }

    fun updateTimer(position: Int, newLabel: String, seconds: Long) {
        try {
            timerList.value?.set(position, Timer(newLabel, seconds))
        }
        catch (e: Exception) {
            println("timerList not initialised")
        }
    }

    fun deleteTimer(position: Int) {
        try {
            timerList.value?.removeAt(position)
        }
        catch (e: Exception) {
            println("timerList not initialised")
        }
    }

    fun startTimer(position: Int) {
        try {
            timerList.value?.get(position)?.startTimer()
        }
        catch (e: Exception) {
            println("Timer doesn't exist or timerlist not initialised")
        }
    }

    fun stopTimer(position: Int) {
        try {
            timerList.value?.get(position)?.stopTimer()
        }
        catch (e: Exception) {
            println("Timer doesn't exist or timerlist not initialised")
        }
    }
}