package com.uszkaisandor.auth.domain

import com.uszkaisandor.core.domain.util.DataError
import com.uszkaisandor.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
}