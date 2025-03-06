package com.geko.challenge.domain.common

sealed class UseCaseResult<out Type, out Failure> {
    data class Succeed<Type>(val data: Type) : UseCaseResult<Type, Nothing>()
    data class Failed<Failure>(val error: Failure) : UseCaseResult<Nothing, Failure>()
}