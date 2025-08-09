package com.elinmejorabletv.ui.mobile.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.elinmejorabletv.databinding.FragmentScheduleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScheduleViewModel by viewModel()
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Initialize UI
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.loadScheduleForDate(year, month, dayOfMonth)
        }

        // Observe view model
        viewModel.scheduleState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScheduleViewModel.ScheduleState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scheduleContent.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                }
                is ScheduleViewModel.ScheduleState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scheduleContent.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                    binding.errorView.visibility = View.GONE

                    // Update UI with schedule data
                    binding.tvScheduleDate.text = state.formattedDate
                    binding.tvScheduleCount.text = "${state.eventCount} carreras programadas"

                    // Update RecyclerView
                    scheduleAdapter.submitList(state.scheduledTracks)
                }
                is ScheduleViewModel.ScheduleState.NoEvents -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scheduleContent.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE

                    binding.tvEmptyTitle.text = "Sin eventos programados"
                    binding.tvEmptyMessage.text = state.message
                }
                is ScheduleViewModel.ScheduleState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scheduleContent.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE
                    binding.errorView.visibility = View.VISIBLE
                    binding.errorView.setErrorMessage(state.message)
                    binding.errorView.setRetryAction {
                        // Retry with current date
                        viewModel.loadScheduleForToday()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        scheduleAdapter = ScheduleAdapter { scheduledTrack ->
            onScheduledTrackClick(scheduledTrack)
        }

        binding.rvSchedule.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = scheduleAdapter
        }
    }

    private fun onScheduledTrackClick(track: ScheduledTrack) {
        if (track.isAvailable) {
            Toast.makeText(
                requireContext(),
                "Detalles de ${track.trackName} - Próximamente",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                "${track.trackName} no está disponible actualmente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}