package com.uszkaisandor.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set

    private val eventChannel = Channel<ActiveRunEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(false)
    private val _hasNotificationPermission = MutableStateFlow(false)

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.OnFinishRunClick -> {}
            ActiveRunAction.OnResumeRunClick -> {}
            ActiveRunAction.OnToggleRunClick -> {}
            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                _hasLocationPermission.value = action.acceptedLocationPermission
                state = state.copy(showLocationRationale = true)
            }

            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                _hasNotificationPermission.value = action.acceptedNotificationPermission
                state = state.copy(showNotificationRationale = true)
            }

            ActiveRunAction.DismissRationaleDialog -> {
                state = state.copy(
                    showNotificationRationale = false,
                    showLocationRationale = false
                )
            }

            ActiveRunAction.OnBackClick -> {}
        }
    }
}