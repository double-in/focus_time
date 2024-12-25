package com.times.focus.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.times.focus.data.db.entity.FocusSession
import com.times.focus.databinding.ItemSessionBinding
import java.text.SimpleDateFormat
import java.util.*

class SessionsAdapter : ListAdapter<FocusSession, SessionsAdapter.SessionViewHolder>(SessionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val binding = ItemSessionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SessionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SessionViewHolder(
        private val binding: ItemSessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

        fun bind(session: FocusSession) {
            binding.apply {
                sessionDate.text = dateFormat.format(session.startTime)
                sessionDuration.text = formatDuration(session.duration.toInt() / 60)
                treeGrowthStatus.text = session.treeGrowthState.name
            }
        }

        private fun formatDuration(minutes: Int): String {
            return when {
                minutes >= 60 -> "${minutes / 60}h ${minutes % 60}m"
                else -> "${minutes}m"
            }
        }
    }

    private class SessionDiffCallback : DiffUtil.ItemCallback<FocusSession>() {
        override fun areItemsTheSame(oldItem: FocusSession, newItem: FocusSession): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FocusSession, newItem: FocusSession): Boolean {
            return oldItem == newItem
        }
    }
} 