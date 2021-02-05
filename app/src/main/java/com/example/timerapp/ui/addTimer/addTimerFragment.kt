package com.example.timerapp.ui.addTimer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.timerapp.R
import com.google.android.material.textfield.TextInputEditText

class AddTimerFragment: Fragment() {

    @SuppressLint("SetTextI18n", "ServiceCast")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val addTimerViewModel = ViewModelProvider(this).get(AddTimerViewModel::class.java)
        val root = inflater.inflate(R.layout.add_timer, container, false)

        val timerTime: TextView = root.findViewById(R.id.timer_time)
        val sliderMins: SeekBar = root.findViewById(R.id.minute_slider)
        val sliderSecs: SeekBar = root.findViewById(R.id.seconds_slider)
        val newLabel: TextInputEditText = root.findViewById(R.id.input_label)
        val cancelButton: Button = root.findViewById(R.id.cancel_button)
        val saveButton: Button = root.findViewById(R.id.save_button)

        val secondsObserver = Observer<Long>{ newSeconds ->
            val minutes = addTimerViewModel.minutes.value!!
            val minutesText = if (minutes < 10) "0$minutes" else "$minutes"
            val secondsText = if (newSeconds < 10) "0$newSeconds" else "$newSeconds"
            timerTime.text = "$minutesText:$secondsText"
        }
        addTimerViewModel.seconds.observe(viewLifecycleOwner, secondsObserver)

        val minutesObserver = Observer<Long>{ newMinutes ->
            val seconds = addTimerViewModel.seconds.value!!
            val minutesText = if (newMinutes < 10) "0$newMinutes" else "$newMinutes"
            val secondsText = if (seconds < 10) "0$seconds" else "$seconds"
            timerTime.text = "$minutesText:$secondsText"
        }
        addTimerViewModel.minutes.observe(viewLifecycleOwner, minutesObserver)

        newLabel.hint = "Type Here"

        //helper function that closes the keyboard automatically
        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        cancelButton.setOnClickListener { view ->
            view.hideKeyboard()
            view.findNavController().navigate(R.id.overview_fragment)
        }

        saveButton.setOnClickListener { view ->
            view.hideKeyboard()
            val bundle = Bundle()
            bundle.putLong("totalSeconds", sliderMins.progress.toLong() * 60 + sliderSecs.progress.toLong())
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
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //updates the viewmodel when the slider changes
        sliderSecs.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                addTimerViewModel.seconds.value = progress.toLong()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return root
    }

}