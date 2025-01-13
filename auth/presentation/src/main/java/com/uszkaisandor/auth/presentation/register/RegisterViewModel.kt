package com.uszkaisandor.auth.presentation.register

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val email = snapshotFlow {
        state.email.text
    }.stateIn(viewModelScope, SharingStarted.Lazily, state.email.text)

    private val password = snapshotFlow {
        state.password.text
    }.stateIn(viewModelScope, SharingStarted.Lazily, state.password.text)

    init {
        email.onEach { email ->
            val isValidEmail = userDataValidator.isValidEmail(email.toString())
            state = state.copy(
                isEmailValid = userDataValidator.isValidEmail(email.toString()),
                canRegister = isValidEmail
                        && state.passwordValidationState.isValidPassword
                        && !state.isRegistering
            )
        }
            .launchIn(viewModelScope)

        password
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid
                            && passwordValidationState.isValidPassword
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> state =
                state.copy(isPasswordVisible = !state.isPasswordVisible)

            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = authRepository.register(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isRegistering = false)

            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        RegisterEvent.Error(
                            if (result.error == DataError.Network.CONFLICT) {
                                UiText.StringResource(R.string.error_email_exists)
                            } else {
                                result.error.asUiText()
                            }
                        )
                    )
                }

                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}