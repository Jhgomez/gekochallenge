package com.geko.challenge.domain

import android.provider.ContactsContract.CommonDataKinds.Email
import com.geko.challenge.core.data.common.DataResult
import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.core.model.User
import com.geko.challenge.domain.common.UseCaseResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator suspend fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): UseCaseResult<User, String> = when (
        val result = authenticationRepository.signUp(firstName, lastName, email, password)
    ) {
        is DataResult.Success -> UseCaseResult.Succeed(result.data)
        is DataResult.Failed -> UseCaseResult.Failed(result.message)
        is DataResult.UnknownFailure -> UseCaseResult.Failed("Unknown Failure")
    }
}