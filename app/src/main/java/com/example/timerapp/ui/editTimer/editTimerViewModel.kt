package com.example.timerapp.ui.editTimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditTimerViewModel: ViewModel() {
    val label: MutableLiveData<String> = MutableLiveData()
    val minutes: MutableLiveData<Long> = MutableLiveData()
    val seconds: MutableLiveData<Long> = MutableLiveData()
}