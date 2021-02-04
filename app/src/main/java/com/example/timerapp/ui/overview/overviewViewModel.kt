package com.example.timerapp.ui.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerapp.model.Timer
import java.lang.Exception

class OverviewViewModel: ViewModel() {
    var timerList: MutableLiveData<MutableList<Timer>> = MutableLiveData(MutableList(0) { index -> Timer("",0)})

    init {
        println("init OverviewViewModel")
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