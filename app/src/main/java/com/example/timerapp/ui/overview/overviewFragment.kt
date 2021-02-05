package com.example.timerapp.ui.overview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timerapp.R
import com.example.timerapp.model.Timer

class OverviewFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val overviewViewModel: OverviewViewModel by activityViewModels()
        val root = inflater.inflate(R.layout.overview_list_view, container, false)
        val timerListView: ListView = root.findViewById(R.id.list_view_timers)

        //the default value of the argument is -1, so if the fragment is opened
        //normally, it won't delete anything
        val timerToDelete =  arguments?.getInt("timerToDelete")
        if (timerToDelete != -1 && timerToDelete != null) {
            overviewViewModel.deleteTimer(timerToDelete)
        }

        //it also can receive information to update a timer. When it does,
        //it looks for a new label and time, and will change it in the list
        val timerToUpdate =  arguments?.getInt("timerToEdit")
        if (timerToUpdate != -1 && timerToUpdate != null) {
            val newLabel = arguments?.getString("label")
            val newTime = arguments?.getLong("totalSeconds")
            if (newLabel != null && newTime != null) {
                overviewViewModel.updateTimer(timerToUpdate, newLabel, newTime)
            }
        }
        else {
            //it can also be called without a timer to update, it will simply
            //create a new timer
            if (arguments?.getString("label") != null) {
                val newLabel = arguments?.getString("label")
                val newTime = arguments?.getLong("totalSeconds")
                if (newLabel != null && newTime != null) {
                    overviewViewModel.addTimer(newLabel, newTime)
                }
            }
        }

        //the observer updates the list of timers (the adapter) when changes occur
        val timersObserver = Observer<MutableList<Timer>>{ liveTimers ->
            val timersAdapter = activity?.let { TimerListAdapter(it, R.layout.timer_list_item, liveTimers) }
            timerListView.adapter = timersAdapter
        }
        overviewViewModel.getTimers().observe(viewLifecycleOwner, timersObserver)

        //navigates to the add timer screen when the button is pressed
        val addTimerButton: Button = root.findViewById(R.id.add_timer_button)
        addTimerButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.add_timer_fragment)
        }

        return root
    }
}

//this adapter manages the display of each element of the list
class TimerListAdapter(context: Context, Id: Int, timers: MutableList<Timer>): ArrayAdapter<Timer>(context, Id, timers) {
    private val localTimers = timers

    @SuppressLint("ViewHolder", "SetTextI18n", "ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.timer_list_item, parent, false)

        //gets all the necessary elements to update
        val remainingTimeView = rowView.findViewById<TextView>(R.id.remaining_time)
        val labelView = rowView.findViewById<TextView>(R.id.label)
        val editButton = rowView.findViewById<Button>(R.id.edit_timer_button)
        val startButton = rowView.findViewById<Button>(R.id.start_timer_button)

        val localRemainingTime = localTimers[position].remainingTime.value
        if (localRemainingTime != null) {
            val remainingMinutesText = if (localRemainingTime / 60 < 10) "0${localRemainingTime/60}" else "${localRemainingTime/60}"
            val remainingSecondsText = if (localRemainingTime % 60 < 10) "0${localRemainingTime%60}" else "${localRemainingTime%60}"
            remainingTimeView.text = "$remainingMinutesText:$remainingSecondsText"
        }
        else {
            remainingTimeView.text = "00:00"
        }
        labelView.text = localTimers[position].name

        editButton.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putInt("position", position)
            localTimers[position].remainingTime.value?.let { bundle.putLong("remainingTime", it) }
            bundle.putString("label", localTimers[position].name)
            view.findNavController().navigate(R.id.edit_timer_fragment, bundle)
        }

        startButton.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putInt("position", position)
            view.findNavController().navigate(R.id.start_timer_fragment, bundle)
        }
        return rowView
    }
}