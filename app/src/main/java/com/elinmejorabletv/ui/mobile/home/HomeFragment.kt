package com.elinmejorabletv.ui.mobile.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.elinmejorabletv.R
import com.elinmejorabletv.databinding.FragmentHomeBinding
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.ui.mobile.MainActivity
import com.elinmejorabletv.ui.mobile.home.adapter.TracksAdapter
import com.elinmejorabletv.ui.player.VideoPlayerActivity
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var tracksAdapter: TracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTabLayout()
        setupSwipeRefresh()

        viewModel.tracksState.observe(viewLifecycleOwner) { state ->
            binding.swipeRefresh.isRefreshing = false

            when (state) {
                is HomeViewModel.TracksState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvTracks.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                }
                is HomeViewModel.TracksState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorView.visibility = View.GONE

                    if (state.tracks.isEmpty()) {
                        binding.rvTracks.visibility = View.GONE
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.rvTracks.visibility = View.VISIBLE
                        binding.emptyView.visibility = View.GONE
                        tracksAdapter.submitList(state.tracks)

                        // Update race count banner
                        val raceCount = state.tracks.size
                        (activity as? MainActivity)?.updateRaceCount(raceCount)
                    }
                }
                is HomeViewModel.TracksState.Empty -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvTracks.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                }
                is HomeViewModel.TracksState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvTracks.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE
                    binding.errorView.visibility = View.VISIBLE
                    binding.errorView.setErrorMessage(state.message)
                    binding.errorView.setRetryAction {
                        viewModel.refreshTracks()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        tracksAdapter = TracksAdapter { track ->
            navigateToPlayer(track)
        }

        binding.rvTracks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tracksAdapter
        }
    }

    private fun setupTabLayout() {
        val tabLayout = (activity as? MainActivity)?.binding?.tabLayout

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.setRegion("") // DESTACADOS (all regions)
                    1 -> viewModel.setRegion("US") // USA
                    2 -> viewModel.setRegion("LATAM") // LATINOAMERICA
                    3 -> viewModel.setRegion("EU") // EUROPA
                    4 -> viewModel.setRegion("ASIA") // ASIA
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshTracks()
        }

        binding.swipeRefresh.setColorSchemeResources(
            R.color.orange,
            R.color.yellow,
            R.color.dark_blue
        )
    }

    private fun navigateToPlayer(track: Track) {
        val intent = Intent(requireContext(), VideoPlayerActivity::class.java).apply {
            putExtra(VideoPlayerActivity.EXTRA_RTMP_URL, track.rtmpUrl)
            putExtra(VideoPlayerActivity.EXTRA_TRACK_NAME, track.name)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}