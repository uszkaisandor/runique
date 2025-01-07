package com.uszkaisandor.auth.data

import com.uszkaisandor.auth.domain.AuthRepository
import com.uszkaisandor.core.data.networking.post
import com.uszkaisandor.core.domain.util.DataError
import com.uszkaisandor.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
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

}