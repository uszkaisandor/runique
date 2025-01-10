package com.uszkaisandor.auth.presentation.login

import com.uszkaisandor.core.presentation.ui.UiText

interface LoginEvent {
    data class Error(val error: UiText) : LoginEvent
    data object LoginSuccess : LoginEvent
}