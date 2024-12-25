package com.times.foucse_i.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        viewModel.statsData.observe(viewLifecycleOwner) { stats ->
            // 更新总览
            binding.totalFocusTime.text = viewModel.formatDuration(stats.totalFocusTime / 60)
            binding.totalSessions.text = stats.totalSessions.toString()

            // 更新时间统计
            binding.weeklyFocusTime.text = viewModel.formatDuration(stats.weeklyFocusTime / 60)
            binding.monthlyFocusTime.text = viewModel.formatDuration(stats.monthlyFocusTime / 60)
            binding.averageFocusTime.text = viewModel.formatDuration(stats.averageFocusTime / 60)
            binding.longestSession.text = viewModel.formatDuration(stats.longestSession / 60)

            // 更新树木统计
            binding.seedTrees.text = stats.seedTrees.toString()
            binding.sproutTrees.text = stats.sproutTrees.toString()
            binding.saplingTrees.text = stats.saplingTrees.toString()
            binding.growingTrees.text = stats.growingTrees.toString()
            binding.matureTrees.text = stats.matureTrees.toString()
            binding.bloomingTrees.text = stats.bloomingTrees.toString()
        }

        viewModel.recentSessions.observe(viewLifecycleOwner) { sessions ->
            sessionsAdapter.submitList(sessions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 