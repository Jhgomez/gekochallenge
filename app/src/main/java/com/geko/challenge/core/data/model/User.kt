package com.geko.challenge.core.data.model

import com.geko.challenge.core.database.model.UserEntity
import com.geko.challenge.core.model.User
import com.geko.challenge.core.network.model.NetworkUser

internal fun UserEntity.toUserModel() = User(
    name = this.name
)

internal fun NetworkUser.toUserEntity() = UserEntity(
    name = this.name
)

internal fun User.toNetworkUser() = NetworkUser(
    name = this.name
)