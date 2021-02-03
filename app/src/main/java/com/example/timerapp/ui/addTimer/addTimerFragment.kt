package com.example.timerapp.ui.addTimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.timerapp.R
import com.google.android.material.textfield.TextInputEditText

class addTimerFragment: Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val addTimerViewModel = ViewModelProvider(this).get(addTimerViewModel::class.java)
        val root = inflater.inflate(R.layout.add_timer, container, false)

        val timerTime: TextView = root.findViewById(R.id.timer_time)
        val sliderMins: SeekBar = root.findViewById(R.id.minute_slider)
        val sliderSecs: SeekBar = root.findViewById(R.id.seconds_slider)
        val newLabel: TextInputEditText = root.findViewById(R.id.input_label)
        val cancelButton: Button = root.findViewById(R.id.cancel_button)
        val saveButton: Button = root.findViewById(R.id.save_button)

        val minutesObserver = Observer<Long>{ newMinutes ->
            timerTime.text = "${newMinutes}:${addTimerViewModel.seconds.value}"
        }
        addTimerViewModel.minutes.observe(viewLifecycleOwner, minutesObserver)

        val secondsObserver = Observer<Long>{ newSeconds ->
            timerTime.text = "${addTimerViewModel.minutes.value}:$newSeconds"
        }
        addTimerViewModel.seconds.observe(viewLifecycleOwner, secondsObserver)

        val labelObserver = Observer<String>{ label ->
            newLabel.hint = label
        }
        addTimerViewModel.label.observe(viewLifecycleOwner, labelObserver)

        cancelButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.overview_fragment)
        }

        saveButton.setOnClickListener { view ->
            val bundle: Bundle = Bundle()
            bundle.putLong("totalSeconds", sliderMins.progress.toLong() * 60 + sliderSecs.progress.toLong())
//            println("dab")
            println(newLabel.text.toString())
//            bundle.putString("label", newLabel.text.toString())
            if (newLabel.text.toString() == "") {
                bundle.putString("label", "New Timer")
            }
            else {
                bundle.putString("label", newLabel.text.toString())
            }
            view.findNavController().navigate(R.id.overview_fragment, bundle)
        }

        //updates the viewmodel when the slider changes
        sliderMins.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                addTimerViewModel.minutes.value = progress.toLong()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        //updates the viewmodel when the slider changes
        sliderSecs.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                addTimerViewModel.seconds.value = progress.toLong()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        return root
    }

}