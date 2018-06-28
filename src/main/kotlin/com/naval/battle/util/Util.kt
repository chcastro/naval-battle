package com.naval.battle.util

import com.naval.battle.model.User
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

val ApplicationCall.user get() = authentication.principal<User>()!!

val testUser = User(1, "Test", listOf("México", "Perú"))
