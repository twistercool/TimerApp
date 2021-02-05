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
import com.example.timerapp.ui.overview.OverviewViewModel

class StartTimerFragment: Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //I could have written a separate viewmodel that receives data from a bundle
        //and sends information to the overview using the same technique, but I feel like it is simpler
        //to simply fetch the timer directly.
        //I can refactor it if it isn't the way to go
        val overviewViewModel: OverviewViewModel by activityViewModels()
        val root = inflater.inflate(R.layout.start_timer, container, false)

        val position = arguments?.getInt("position")!!

        val timerTextView = root.findViewById<TextView>(R.id.current_timer)
        val originalTimerTextView = root.findViewById<TextView>(R.id.start_timer)
        val startOrPauseButton = root.findViewById<Button>(R.id.start_or_pause_button)
        val doneButton = root.findViewById<Button>(R.id.done_button)

        //shows the remaining time of the timer
        val labelObserver = Observer<Long>{ liveTime ->
            val remainingMinutesText = if (liveTime / 60 < 10) "0${liveTime/60}" else "${liveTime/60}"
            val remainingSecondsText = if (liveTime % 60 < 10) "0${liveTime%60}" else "${liveTime%60}"
            timerTextView.text = "$remainingMinutesText:$remainingSecondsText"
            if (liveTime.toInt() == 0) {
                root.findNavController().navigate(R.id.overview_fragment)
            }
        }
        overviewViewModel.getRemainingTime(position)?.observe(viewLifecycleOwner, labelObserver)

        //shows the original time when the timer was create
        val originalTime = overviewViewModel.getOriginalTime(position)
        if (originalTime != null) {
            val originalMinutes = if (originalTime / 60 < 10) "0${originalTime/60}" else "${originalTime/60}"
            val originalSeconds = if (originalTime % 60 < 10) "0${originalTime%60}" else "${originalTime%60}"
            originalTimerTextView.text = "$originalMinutes:$originalSeconds"
        }

        //manages the logic of pausing and starting the timer
        startOrPauseButton.setOnClickListener { _ ->
            when (overviewViewModel.isTimerPaused(position)) {
                true -> {
                    overviewViewModel.startTimer(position)
                    startOrPauseButton.text = "Pause"
                }
                false -> {
                    overviewViewModel.stopTimer(position)
                    startOrPauseButton.text = "Continue"
                }
                else -> {
                    println("can't access the timer")
                }
            }
        }

        //stops the timer before navigating ti the overview
        doneButton.setOnClickListener { view ->
            overviewViewModel.stopTimer(position)
            view.findNavController().navigate(R.id.overview_fragment)
        }

        //starts with running the timer
        overviewViewModel.startTimer(position)

        return root
    }
}