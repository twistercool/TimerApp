package com.example.timerapp.ui.addTimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTimerViewModel: ViewModel() {
    val minutes: MutableLiveData<Long> = MutableLiveData()
    val seconds: MutableLiveData<Long> = MutableLiveData()


    init {
        minutes.value = 1
        seconds.value = 0
    }
}