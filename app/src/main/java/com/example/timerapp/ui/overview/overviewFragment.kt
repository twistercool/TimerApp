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

        //if the fragment has been created with arguments, it means that
        //addTimerFragment sends a bundle with a new timer, and should therefore be added
        if (arguments?.getString("label") != null)
        {
            requireArguments().getString("label")?.let { overviewViewModel.addTimer(it, requireArguments().getLong("totalSeconds")) }
        }


        val timersObserver = Observer<MutableList<Timer>>{ liveTimers ->
            //let and it get the context of the fragment
            val timersAdapter = activity?.let { TimerListAdapter(it, R.layout.timer_list_item, liveTimers) }
            timerListView.adapter = timersAdapter
        }
        overviewViewModel.timerList.observe(viewLifecycleOwner, timersObserver)


        val addTimerButton: Button = root.findViewById(R.id.add_timer_button)
        addTimerButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.add_timer_fragment)
        }

        return root
    }
}

class TimerListAdapter(context: Context, Id: Int, timers: MutableList<Timer>): ArrayAdapter<Timer>(context, Id, timers) {
    private val localTimers = timers

    @SuppressLint("ViewHolder", "SetTextI18n", "ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.timer_list_item, parent, false)

        val remainingTimeView = rowView.findViewById<TextView>(R.id.remaining_time)
        val labelView = rowView.findViewById<TextView>(R.id.label)
        val editButton = rowView.findViewById<Button>(R.id.edit_timer_button)
        val startButton = rowView.findViewById<Button>(R.id.start_timer_button)

        val localRemainingTime = localTimers[position].remainingTime.value
        if (localRemainingTime != null) {
            remainingTimeView.text = "${localRemainingTime / 60}:${localRemainingTime % 60}"
        }
        else {
            remainingTimeView.text = "00:00"
        }
        labelView.text = localTimers[position].name

        startButton.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putInt("position", position)
            view.findNavController().navigate(R.id.start_timer_fragment, bundle)
        }
        return rowView
    }
}