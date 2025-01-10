package com.uszkaisandor.run.network.mapper

import com.uszkaisandor.core.domain.location.Location
import com.uszkaisandor.core.domain.run.Run
import com.uszkaisandor.run.network.CreateRunRequest
import com.uszkaisandor.run.network.RunDto
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Duration.Companion.milliseconds

fun RunDto.toRun(): Run {
    return Run(
        id = id,
        dateTimeUtc = Instant.parse(dateTimeUtc)
            .atZone(ZoneId.of("UTC")),
        duration = durationMillis.milliseconds,
        distanceMeters = distanceMeters,
        location = Location(lat, long),
        maxSpeedKmh = maxSpeedKmh,
        totalElevationMeters = totalElevationMeters,
        mapPictureUrl = mapPictureUrl
    )
}

fun Run.toCreateRunRequest(): CreateRunRequest {
    return CreateRunRequest(
        id = id!!,
        epochMillis = dateTimeUtc.toEpochSecond() * 1000L,
        durationMillis = duration.inWholeMilliseconds,
        distanceMeters = distanceMeters,
        maxSpeedKmh = maxSpeedKmh,
        lat = location.lat,
        long = location.long,
        avgSpeedKmh = avgSpeedKmh,
        totalElevationMeters = totalElevationMeters
    )
}