package com.elinmejorabletv.ui.mobile.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elinmejorabletv.data.preferences.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class AccountViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _accountState = MutableLiveData<AccountState>(AccountState.Loading)
    val accountState: LiveData<AccountState> = _accountState

    private val _notificationsEnabled = MutableLiveData(true)
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    private val _darkModeEnabled = MutableLiveData(false)
    val darkModeEnabled: LiveData<Boolean> = _darkModeEnabled

    init {
        loadAccountDetails()
        _notificationsEnabled.value = userPreferences.notificationsEnabled
        _darkModeEnabled.value = userPreferences.darkModeEnabled
    }

    fun loadAccountDetails() {
        viewModelScope.launch {
            _accountState.value = AccountState.Loading

            delay(500) // Simular carga de datos

            try {
                if (userPreferences.isLoggedIn) {
                    _accountState.value = AccountState.Success(
                        username = userPreferences.username,
                        email = userPreferences.email
                    )
                } else {
                    // Para demo, usamos datos ficticios
                    _accountState.value = AccountState.Success(
                        username = "PedroRiveraV",
                        email = "pedro@ejemplo.com"
                    )

                    // Guardamos estos datos demo en preferencias
                    userPreferences.username = "PedroRiveraV"
                    userPreferences.email = "pedro@ejemplo.com"
                    userPreferences.isLoggedIn = true
                    userPreferences.lastLoginDate = Date().time
                }
            } catch (e: Exception) {
                _accountState.value = AccountState.Error(
                    message = e.message ?: "Error desconocido al cargar datos de la cuenta"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.isLoggedIn = false
            _accountState.value = AccountState.Loading
            delay(500)
            _accountState.value = AccountState.LoggedOut
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        userPreferences.notificationsEnabled = enabled
        _notificationsEnabled.value = enabled
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        userPreferences.darkModeEnabled = enabled
        _darkModeEnabled.value = enabled
    }

    sealed class AccountState {
        object Loading : AccountState()
        data class Success(val username: String, val email: String) : AccountState()
        data class Error(val message: String) : AccountState()
        object LoggedOut : AccountState()
    }
}