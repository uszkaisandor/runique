package com.uszkaisandor.auth.data

import com.uszkaisandor.auth.domain.AuthRepository
import com.uszkaisandor.core.data.networking.post
import com.uszkaisandor.core.domain.AuthInfo
import com.uszkaisandor.core.domain.SessionStorage
import com.uszkaisandor.core.domain.util.DataError
import com.uszkaisandor.core.domain.util.EmptyResult
import com.uszkaisandor.core.domain.util.Result
import com.uszkaisandor.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }

}