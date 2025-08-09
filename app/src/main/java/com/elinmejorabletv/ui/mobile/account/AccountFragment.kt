package com.elinmejorabletv.ui.mobile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.elinmejorabletv.databinding.FragmentAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        binding.tvAccountTitle.text = "Mi Cuenta"

        // Setup switches
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationsEnabled(isChecked)
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkModeEnabled(isChecked)
            Toast.makeText(requireContext(), "Esta funcionalidad estará disponible próximamente", Toast.LENGTH_SHORT).show()
        }

        // Observe viewmodel states
        viewModel.accountState.observe(viewLifecycleOwner) { state ->
            // Update UI based on state
            when (state) {
                is AccountViewModel.AccountState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.contentLayout.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                }
                is AccountViewModel.AccountState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE
                    binding.errorView.visibility = View.GONE

                    // Update UI with user data
                    binding.tvUsername.text = state.username
                    binding.tvEmail.text = state.email
                }
                is AccountViewModel.AccountState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.GONE
                    binding.errorView.visibility = View.VISIBLE
                    binding.errorView.setErrorMessage(state.message)
                    binding.errorView.setRetryAction {
                        viewModel.loadAccountDetails()
                    }
                }
                is AccountViewModel.AccountState.LoggedOut -> {
                    Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    viewModel.loadAccountDetails()
                }
            }
        }

        // Observe preference states
        viewModel.notificationsEnabled.observe(viewLifecycleOwner) { enabled ->
            binding.switchNotifications.isChecked = enabled
        }

        viewModel.darkModeEnabled.observe(viewLifecycleOwner) { enabled ->
            binding.switchDarkMode.isChecked = enabled
        }

        // Button click listeners
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Esta funcionalidad estará disponible próximamente", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}