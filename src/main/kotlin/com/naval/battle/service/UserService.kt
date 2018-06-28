package com.naval.battle.service

import com.naval.battle.model.User
import io.ktor.auth.*

interface UserSource {

    fun findUserById(id: Int): User

    fun findUserByCredentials(credential: UserPasswordCredential): User

}