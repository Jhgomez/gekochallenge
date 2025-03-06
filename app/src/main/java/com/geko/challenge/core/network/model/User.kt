package com.geko.challenge.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser(
    val name: String = ""
)


//fun NetworkUser.asDomainModel(): User =
//    User(
//        name = name
//    )
