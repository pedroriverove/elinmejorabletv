package com.elinmejorabletv.ui.mobile.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.elinmejorabletv.R
import com.elinmejorabletv.databinding.ItemTrackBinding
import com.elinmejorabletv.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TracksAdapter(private val onItemClick: (Track) -> Unit) :
    ListAdapter<Track, TracksAdapter.TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrackViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrackViewHolder(
        private val binding: ItemTrackBinding,
        private val onItemClick: (Track) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

        fun bind(track: Track) {
            binding.apply {
                tvTrackName.text = track.name
                tvLocation.text = track.state
                tvStartsAt.text = itemView.context.getString(R.string.starts_at)
                tvTime.text = timeFormat.format(track.lastUpdated)
                tvRacesCount.text = itemView.context.getString(R.string.races_count, "12") // Hardcoded for now

                // Load track logo (assuming we have a helper function to get logo URL)
                val logoUrl = getLogoUrl(track.trackId)
                ivTrackLogo.load(logoUrl) {
                    placeholder(R.drawable.placeholder_track)
                    error(R.drawable.placeholder_track)
                }

                // Set live indicator if track is streaming
                ivLiveIndicator.visibility = if (track.isStreaming) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }

                root.setOnClickListener { onItemClick(track) }
            }
        }

        private fun getLogoUrl(trackId: String): String {
            // This would ideally come from your API
            return "https://example.com/logos/$trackId.png"
        }
    }

    class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.trackId == newItem.trackId
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}