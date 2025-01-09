package com.uszkaisandor.core.data.run

import com.uszkaisandor.core.domain.run.LocalRunDataSource
import com.uszkaisandor.core.domain.run.RemoteRunDataSource
import com.uszkaisandor.core.domain.run.Run
import com.uszkaisandor.core.domain.run.RunId
import com.uszkaisandor.core.domain.run.RunRepository
import com.uszkaisandor.core.domain.util.DataError
import com.uszkaisandor.core.domain.util.EmptyResult
import com.uszkaisandor.core.domain.util.Result
import com.uszkaisandor.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope
) : RunRepository {

    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when (val result = remoteRunDataSource.getRuns()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDataSource.postRun(
            runWithId, mapPicture
        )

        return when (remoteResult) {
            is Result.Error -> {
                // TODO later
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.deleteRun(id)

        applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()

    }

}