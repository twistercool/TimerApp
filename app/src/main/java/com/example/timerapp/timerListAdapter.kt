package com.example.timerapp
//
//import android.annotation.SuppressLint
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.timerapp.model.Timer
//
//class TimerListAdapter: ListAdapter<Timer, TimerListAdapter.timerHolder>(TimerDiffCallback()),
//    android.widget.ListAdapter {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): timerHolder {
//        return timerHolder(60, "New Timer", parent)
//    }
//
//    override fun onBindViewHolder(myViewHolder: timerHolder, position: Int) {
//        val item = getItem(position)
//        myViewHolder.bindTo()
//    }
//
//    class timerHolder(seconds: Long, label: String, itemView: View): RecyclerView.ViewHolder(itemView) {
//        val secs: Long = seconds
//        val name: String = label
//        fun bindTo() {
//
//        }
//    }
//
//
//}
//
//
//
//
//
//class TimerDiffCallback : DiffUtil.ItemCallback<Timer>() {
//    override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
//        return oldItem.name == newItem.name && oldItem.globalTime == newItem.globalTime && oldItem.remainingTime == newItem.remainingTime
//    }
//
//    @SuppressLint("DiffUtilEquals")
//    override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
//        return oldItem == newItem
//    }
//}