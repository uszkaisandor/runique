package com.uszkaisandor.core.domain.run

import com.uszkaisandor.core.domain.util.DataError
import com.uszkaisandor.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    fun getRuns(): Flow<List<Run>>
    suspend fun fetchRuns(): EmptyResult<DataError>
    suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError>
    suspend fun deleteRun(id: RunId)
    suspend fun syncPendingRuns()
    suspend fun logout(): EmptyResult<DataError.Network>
    suspend fun deleteAllRuns()
}