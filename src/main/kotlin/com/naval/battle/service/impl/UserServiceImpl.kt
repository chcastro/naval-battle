package com.naval.battle.service.impl

import com.naval.battle.model.User
import com.naval.battle.service.UserSource
import com.naval.battle.util.testUser
import io.ktor.auth.UserPasswordCredential

class UserSourceImpl : UserSource {

    override fun findUserById(id: Int): User {
        return users[id]!!
    }

    override fun findUserByCredentials(credential: UserPasswordCredential): User {
        return testUser
    }

    private val users = listOf(testUser).associateBy(User::id)

}