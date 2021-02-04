package com.example.timerapp.ui.startTimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.timerapp.R
import com.example.timerapp.model.Timer
import com.example.timerapp.ui.overview.OverviewViewModel

class StartTimerFragment: Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val overviewViewModel: OverviewViewModel by activityViewModels()
        val root = inflater.inflate(R.layout.start_timer, container, false)

        val position = arguments?.getInt("position")

        val timerTextView = root.findViewById<TextView>(R.id.current_timer)
        val originalTimerTextView = root.findViewById<TextView>(R.id.start_timer)
        val startOrPauseButton = root.findViewById<Button>(R.id.start_or_pause_button)
        val doneButton = root.findViewById<Button>(R.id.done_button)

        //shows the remaining time of the timer
        val timer = position?.let { overviewViewModel.timerList.value?.get(it) }
        val labelObserver = Observer<Long>{ liveTime ->
            timerTextView.text = "${liveTime/60}:${liveTime%60}"
            if (liveTime.toInt() == 0) {
                if (position != null) {
                    overviewViewModel.timerList.value?.removeAt(position)
                }
                root.findNavController().navigate(R.id.overview_fragment)
            }
        }
        timer?.remainingTime?.observe(viewLifecycleOwner, labelObserver)

        //shows the original time when the timer was created
        val originalTime = position?.let { overviewViewModel.timerList.value?.get(it)?.globalTime }
        if (originalTime != null) {
            originalTimerTextView.text = "${originalTime/60}:${originalTime%60}"
        }

        startOrPauseButton.setOnClickListener { view ->
            if (position?.let { overviewViewModel.timerList.value?.get(it)?.isPaused} == true) {
                overviewViewModel.timerList.value?.get(position.toInt())?.startTimer()
                startOrPauseButton.text = "Pause"
            }
            else if (position?.let { overviewViewModel.timerList.value?.get(it)?.isPaused} == false) {
                overviewViewModel.timerList.value?.get(position.toInt())?.stopTimer()
                startOrPauseButton.text = "Start"
            }
        }

        doneButton.setOnClickListener { view ->
            if (position != null) {
                overviewViewModel.timerList.value?.get(position)?.stopTimer()
            }
            view.findNavController().navigate(R.id.overview_fragment)
        }

        if (position != null) {
            overviewViewModel.timerList.value?.get(position)?.startTimer()
        }

        return root
    }
}