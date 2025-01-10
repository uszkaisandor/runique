package com.uszkaisandor.auth.presentation.register

import com.uszkaisandor.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data class Error(val error: UiText) : RegisterEvent
}