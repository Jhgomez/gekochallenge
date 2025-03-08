package com.geko.challenge.domain

import com.geko.challenge.core.data.common.DataResult
import com.geko.challenge.core.data.repository.UserRepository
import com.geko.challenge.core.model.User
import com.geko.challenge.domain.common.UseCaseResult
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator suspend fun invoke(): UseCaseResult<User, String> = when (
        val result = userRepository.getUserData()
    ) {
        is DataResult.Success -> UseCaseResult.Succeed(result.data)
        is DataResult.Failed -> UseCaseResult.Failed(result.message)
        is DataResult.UnknownFailure -> UseCaseResult.Failed("Unknown Failure")
    }
}