package com.example.timerapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.timerapp.model.Timer
import com.example.timerapp.ui.overview.OverviewViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overview_list_view)

        //gets an instance of the viewModel of the overview
//        val overviewViewModel: OverviewViewModel by viewModels()
        val overviewViewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
        val timerListView: ListView = findViewById(R.id.list_view_timers)


        val timersObserver = Observer<MutableList<Timer>>{ liveTimers ->
            val timersAdapter = TimerListAdapter(this, R.layout.timer_list_item, liveTimers)
            timerListView.adapter = timersAdapter
        }
        overviewViewModel.timerList.observe(this, timersObserver)


    }
}

class TimerListAdapter(context: Context, Id: Int, timers: MutableList<Timer>): ArrayAdapter<Timer>(context, Id, timers) {
    val localTimers = timers

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.timer_list_item, parent, false)

        val remainingTimeView = rowView.findViewById<TextView>(R.id.remaining_time)
        val labelView = rowView.findViewById<TextView>(R.id.label)

        val localRemainingTime = localTimers[position].remainingTime.value
        if (localRemainingTime != null) {
            remainingTimeView.text = "${localRemainingTime / 60}:${localRemainingTime % 60}"
        }
        else {
            remainingTimeView.text = "00:00"
        }
        labelView.text = localTimers[position].name
        return rowView
    }
} 