package com.example.timerapp.ui.editTimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.timerapp.R
import com.google.android.material.textfield.TextInputEditText

class EditTimerFragment: Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val editTimerViewModel: EditTimerViewModel by viewModels()
        val root = inflater.inflate(R.layout.edit_timer, container, false)

        val timerTime: TextView = root.findViewById(R.id.timer_time_edit_screen)
        val sliderMins: SeekBar = root.findViewById(R.id.edit_minute_slider)
        val sliderSecs: SeekBar = root.findViewById(R.id.edit_seconds_slider)
        val newLabel: TextInputEditText = root.findViewById(R.id.input_label_edit)
        val deleteButton: Button = root.findViewById(R.id.delete_button)
        val cancelButton: Button = root.findViewById(R.id.cancel_button_edit)
        val saveButton: Button = root.findViewById(R.id.save_edit_button)

        val originalLabel = arguments?.getString("label")
        newLabel.hint = originalLabel
        val originalTime = arguments?.getLong("remainingTime")?.toInt()
        if (originalTime != null) {
            editTimerViewModel.minutes.value = originalTime.toLong() / 60
            editTimerViewModel.seconds.value = originalTime.toLong() % 60
            sliderMins.progress = originalTime / 60
            sliderSecs.progress = originalTime % 60
        }

        val secObserver = Observer<Long>{ newSeconds ->
            timerTime.text = "${editTimerViewModel.minutes.value}:$newSeconds"
        }
        editTimerViewModel.seconds.observe(viewLifecycleOwner, secObserver)

        val minObserver = Observer<Long>{ newMinutes ->
            timerTime.text = "${newMinutes}:${editTimerViewModel.seconds.value}"
        }
        editTimerViewModel.minutes.observe(viewLifecycleOwner, minObserver)


        //updates the viewmodel when the slider changes
        sliderSecs.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editTimerViewModel.seconds.value = progress.toLong()
            }
            //we have to implement the interface and therefore those two functions have to be there
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //updates the viewmodel when the slider changes
        sliderMins.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editTimerViewModel.minutes.value = progress.toLong()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        deleteButton.setOnClickListener { view ->
            val bundle = Bundle()
            arguments?.getInt("position")?.let { bundle.putInt("timerToDelete", it) }
            view.findNavController().navigate(R.id.overview_fragment, bundle)
        }

        cancelButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.overview_fragment)
        }

        saveButton.setOnClickListener { view ->
            val bundle = Bundle()
            arguments?.getInt("position")?.let { bundle.putInt("timerToEdit", it) }
            //puts the total seconds in the bundle
            editTimerViewModel.minutes.value?.times(60)?.plus(editTimerViewModel.seconds.value!!)?.let { bundle.putLong("totalSeconds", it) }
            if (newLabel.text.toString() == "") {
                bundle.putString("label", originalLabel)
            }
            else {
                bundle.putString("label", newLabel.text.toString())
            }
            view.findNavController().navigate(R.id.overview_fragment, bundle)
        }

        return root
    }
}