package com.uszkaisandor.run.location

import android.location.Location
import com.uszkaisandor.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.uszkaisandor.core.domain.location.Location(
            lat = latitude,
            lng = longitude
        ),
        altitude = altitude
    )
}