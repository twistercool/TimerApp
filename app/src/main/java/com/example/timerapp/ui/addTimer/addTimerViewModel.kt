package com.example.timerapp.ui.addTimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTimerViewModel: ViewModel() {
    val label: MutableLiveData<String> = MutableLiveData()
    val minutes: MutableLiveData<Long> = MutableLiveData()
    val seconds: MutableLiveData<Long> = MutableLiveData()


    init {
        label.value = "Type Here"
        minutes.value = 1
        seconds.value = 0
    }
}