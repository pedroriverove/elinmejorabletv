package com.elinmejorabletv.ui.mobile.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.elinmejorabletv.R
import com.elinmejorabletv.databinding.ItemScheduledTrackBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ScheduleAdapter(private val onItemClick: (ScheduledTrack) -> Unit) :
    ListAdapter<ScheduledTrack, ScheduleAdapter.ScheduleViewHolder>(ScheduleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduledTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScheduleViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ScheduleViewHolder(
        private val binding: ItemScheduledTrackBinding,
        private val onItemClick: (ScheduledTrack) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(scheduleItem: ScheduledTrack) {
            binding.apply {
                tvTrackName.text = scheduleItem.trackName
                tvLocation.text = scheduleItem.location
                tvTime.text = timeFormat.format(scheduleItem.startTime)
                tvRacesCount.text = itemView.context.getString(
                    R.string.races_count,
                    scheduleItem.racesCount.toString()
                )

                // Load track logo
                ivTrackLogo.load(scheduleItem.imageUrl) {
                    placeholder(R.drawable.placeholder_track)
                    error(R.drawable.placeholder_track)
                }

                root.setOnClickListener { onItemClick(scheduleItem) }
            }
        }
    }

    class ScheduleDiffCallback : DiffUtil.ItemCallback<ScheduledTrack>() {
        override fun areItemsTheSame(oldItem: ScheduledTrack, newItem: ScheduledTrack): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScheduledTrack, newItem: ScheduledTrack): Boolean {
            return oldItem == newItem
        }
    }
}