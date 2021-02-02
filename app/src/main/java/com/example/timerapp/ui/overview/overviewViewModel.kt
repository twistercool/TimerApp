package com.example.timerapp.ui.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerapp.model.Timer
import java.lang.Exception

class OverviewViewModel: ViewModel() {
    lateinit var timerList: MutableLiveData<MutableList<Timer>>

    init {
        //initialise it for testing
        timerList = MutableLiveData(MutableList(5) {index -> Timer("New Timer", 60)})
    }

    //default timer is set to
    fun addTimer() {
        try {
            timerList.value?.add(Timer("New Timer", 60))
        }
        catch (e: Exception) {
            println("timerList not initialised")
        }
    }

    fun addTimer(label: String, seconds: Long) {
        try {
            timerList.value?.add(Timer(label, seconds))
        }
        catch (e: Exception) {
            println("timerList not initialised")
        }
    }

    fun startTimer(index: Int) {
        try {
            timerList.value?.get(index)?.startTimer()
        }
        catch (e: Exception) {
            println("Timer doesn't exist or timerlist not initialised")
        }
    }

    fun stopTimer(index: Int) {
        try {
            timerList.value?.get(index)?.stopTimer()
        }
        catch (e: Exception) {
            println("Timer doesn't exist or timerlist not initialised")
        }
    }
}