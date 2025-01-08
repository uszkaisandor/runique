@file:OptIn(ExperimentalCoroutinesApi::class)

package com.uszkaisandor.run.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class RunningTracker(
    private val locationObserver: LocationObserver,
    applicationScope: CoroutineScope
) {

    private val isObservingLocation = MutableStateFlow(false)

    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->
            if (isObservingLocation) {
                locationObserver.observerLocation(1000L)
            } else flowOf()
        }
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    fun startObservingLocation() {
        isObservingLocation.value = true
    }

    fun stopObservingLocation() {
        isObservingLocation.value = false
    }

}