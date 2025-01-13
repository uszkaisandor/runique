package com.uszkaisandor.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uszkaisandor.auth.domain.AuthRepository
import com.uszkaisandor.auth.domain.UserDataValidator
import com.uszkaisandor.auth.presentation.R
import com.uszkaisandor.core.domain.util.DataError
import com.uszkaisandor.core.domain.util.Result
import com.uszkaisandor.core.presentation.ui.UiText
import com.uszkaisandor.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val email = snapshotFlow {
        state.email.text
    }.stateIn(viewModelScope, SharingStarted.Lazily, state.email.text)

    private val password = snapshotFlow {
        state.password.text
    }.stateIn(viewModelScope, SharingStarted.Lazily, state.password.text)

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(email, password) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(email.toString().trim())
                        && password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLoggingIn = false)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        if (result.error != DataError.Network.UNAUTHORIZED) {
                            LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        } else {
                            LoginEvent.Error(result.error.asUiText())
                        }
                    )
                }

                is Result.Success -> eventChannel.send(LoginEvent.LoginSuccess)
            }
        }
    }
}