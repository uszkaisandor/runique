package com.uszkaisandor.run.domain

import com.uszkaisandor.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observerLocation(interval: Long): Flow<LocationWithAltitude>
}