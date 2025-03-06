package com.geko.challenge.domain

import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.domain.common.UseCaseResult
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator suspend fun invoke(): UseCaseResult<Unit, String> {
        authenticationRepository.logout()
        return UseCaseResult.Succeed(Unit)
    }
}