package com.times.foucse_i.ui.timer

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.viewModels
import com.times.foucse_i.R
import com.times.foucse_i.databinding.FragmentTimerBinding
import com.times.foucse_i.ui.base.BaseFragment
import com.times.foucse_i.util.NotificationUtil
import com.times.foucse_i.util.SoundUtil
import com.times.foucse_i.util.WindowUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerFragment : BaseFragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TimerViewModel by viewModels()

    private var treeAnimator: ValueAnimator? = null
    private var progressAnimator: ValueAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeState()
//        setupWindowInsets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        treeAnimator?.cancel()
        progressAnimator?.cancel()
        _binding = null
    }

    override fun isLightStatusBar(): Boolean = true

    private fun setupUI() {
        binding.startButton.setOnClickListener {
            when (viewModel.uiState.value?.timerState) {
                is TimerState.Running -> viewModel.stopTimer()
                is TimerState.Paused -> viewModel.startTimer()
                else -> viewModel.startTimer()
            }
        }

        binding.pauseButton.setOnClickListener {
            viewModel.pauseTimer()
        }
    }

    private fun observeState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            updateUI(state)
        }
    }

    @SuppressLint("SetTextI18s", "DefaultLocale")
    private fun updateUI(state: TimerUiState) {
        // 更新时间显示
        val minutes = state.remainingTime / 60
        val seconds = state.remainingTime % 60
        binding.timerTextView.text = String.format("%02d:%02d", minutes, seconds)
        
        // 更新进度条（带动画）
        animateProgress(state.progress)
        
        // 更新树的状态（带动画）
        animateTreeState(state.treeState)
        
        // 更新树木图标
        val treeDrawable = when (state.treeState) {
            TreeGrowthState.SEED -> R.drawable.tree_seed
            TreeGrowthState.SPROUT -> R.drawable.tree_sprout
            TreeGrowthState.SAPLING -> R.drawable.tree_sapling
            TreeGrowthState.GROWING -> R.drawable.tree_growing
            TreeGrowthState.MATURE -> R.drawable.tree_mature
            TreeGrowthState.BLOOMING -> R.drawable.tree_blooming
        }
        binding.treeImage.setImageResource(treeDrawable)
        
        // 更新UI状态
        when (state.timerState) {
            is TimerState.Running -> {
                binding.startButton.text = getString(R.string.stop)
                binding.pauseButton.visibility = View.VISIBLE
                activity?.window?.let { WindowUtil.enterFocusMode(it) }
            }
            is TimerState.Paused -> {
                binding.startButton.text = getString(R.string.resume)
                binding.pauseButton.visibility = View.GONE
                activity?.window?.let { WindowUtil.exitFocusMode(it) }
            }
            is TimerState.Finished.Focus -> {
                binding.startButton.text = getString(R.string.start_break)
                binding.pauseButton.visibility = View.GONE
                activity?.window?.let { WindowUtil.exitFocusMode(it) }
                context?.let {
                    if (state.notificationsEnabled) {
                        NotificationUtil.showTimerCompleteNotification(
                            it,
                            state.focusMinutes,
                            state.totalTrees
                        )
                    }
                    if (state.vibrationEnabled) {
                        NotificationUtil.vibrate(it)
                    }
                }
            }
            is TimerState.Finished.ShortBreak, is TimerState.Finished.LongBreak -> {
                binding.startButton.text = getString(R.string.start_focus)
                binding.pauseButton.visibility = View.GONE
                activity?.window?.let { WindowUtil.exitFocusMode(it) }
                context?.let {
                    if (state.notificationsEnabled) {
                        NotificationUtil.showTimerCompleteNotification(
                            it,
                            state.focusMinutes,
                            state.totalTrees
                        )
                    }
                    if (state.vibrationEnabled) {
                        NotificationUtil.vibrate(it)
                    }
                }
            }
            is TimerState.Idle -> {
                binding.startButton.text = when (state.timerType) {
                    TimerType.FOCUS -> getString(R.string.plant_a_tree)
                    TimerType.SHORT_BREAK -> getString(R.string.take_a_break)
                    TimerType.LONG_BREAK -> getString(R.string.take_a_long_break)
                }
                binding.pauseButton.visibility = View.GONE
                activity?.window?.let { WindowUtil.exitFocusMode(it) }
            }
        }
    }

    private fun animateProgress(targetProgress: Float) {
        progressAnimator?.cancel()
        progressAnimator = ValueAnimator.ofFloat(
            binding.progressIndicator.progress / 100f,
            targetProgress
        ).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                binding.progressIndicator.progress = ((animator.animatedValue as Float) * 100).toInt()
            }
            start()
        }
    }

    private fun animateTreeState(treeState: TreeGrowthState) {
        val treeDescription = when (treeState) {
            TreeGrowthState.SEED -> getString(R.string.tree_state_seed)
            TreeGrowthState.SPROUT -> getString(R.string.tree_state_sprout)
            TreeGrowthState.SAPLING -> getString(R.string.tree_state_sapling)
            TreeGrowthState.GROWING -> getString(R.string.tree_state_growing)
            TreeGrowthState.MATURE -> getString(R.string.tree_state_mature)
            TreeGrowthState.BLOOMING -> getString(R.string.tree_state_blooming)
        }

        treeAnimator?.cancel()
        treeAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val alpha = animator.animatedValue as Float
                binding.treeStateText.alpha = alpha
                binding.treeStateText.scaleX = 0.8f + (0.2f * alpha)
                binding.treeStateText.scaleY = 0.8f + (0.2f * alpha)
            }
            start()
        }
        binding.treeStateText.text = treeDescription
    }


    companion object {
        fun newInstance() = TimerFragment()
    }
} 