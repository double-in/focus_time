package com.times.foucse_i.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.times.foucse_i.R
import com.times.foucse_i.databinding.FragmentStatsBinding
import com.times.foucse_i.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : BaseFragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatsViewModel by viewModels()
    private lateinit var sessionsAdapter: SessionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    override fun isLightStatusBar(): Boolean = true

    private fun setupRecyclerView() {
        sessionsAdapter = SessionsAdapter()
        binding.sessionsRecyclerView.apply {
            adapter = sessionsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.totalFocusTime.observe(viewLifecycleOwner) { totalTime ->
            binding.totalFocusTime.text = formatTime(totalTime)
        }

        viewModel.treesPlanted.observe(viewLifecycleOwner) { trees ->
            binding.treesPlantedCount.text = trees.toString()
        }

        viewModel.recentSessions.observe(viewLifecycleOwner) { sessions ->
            sessionsAdapter.submitList(sessions)
        }
    }

    private fun formatTime(minutes: Long): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return when {
            hours > 0 -> "$hours h $remainingMinutes min"
            else -> "$remainingMinutes min"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 