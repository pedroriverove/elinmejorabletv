package com.elinmejorabletv.ui.mobile.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.elinmejorabletv.databinding.FragmentFavoritesBinding
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.ui.mobile.home.adapter.TracksAdapter
import com.elinmejorabletv.ui.player.VideoPlayerActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var tracksAdapter: TracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.favoriteTracks.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoritesViewModel.FavoriteTracksState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvFavorites.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                }
                is FavoritesViewModel.FavoriteTracksState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorView.visibility = View.GONE

                    if (state.tracks.isEmpty()) {
                        binding.rvFavorites.visibility = View.GONE
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.rvFavorites.visibility = View.VISIBLE
                        binding.emptyView.visibility = View.GONE
                        tracksAdapter.submitList(state.tracks)
                    }
                }
                is FavoritesViewModel.FavoriteTracksState.Empty -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvFavorites.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                }
                is FavoritesViewModel.FavoriteTracksState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvFavorites.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE
                    binding.errorView.visibility = View.VISIBLE
                    binding.errorView.setErrorMessage(state.message)
                    binding.errorView.setRetryAction {
                        viewModel.loadFavoriteTracks()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        tracksAdapter = TracksAdapter { track ->
            navigateToPlayer(track)
        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tracksAdapter
        }
    }

    private fun navigateToPlayer(track: Track) {
        val intent = Intent(requireContext(), VideoPlayerActivity::class.java).apply {
            putExtra(VideoPlayerActivity.EXTRA_RTMP_URL, track.rtmpUrl)
            putExtra(VideoPlayerActivity.EXTRA_TRACK_NAME, track.name)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavoriteTracks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}