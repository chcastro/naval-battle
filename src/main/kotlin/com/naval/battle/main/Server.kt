package com.naval.battle.main

import com.naval.battle.module.module
import io.ktor.server.netty.Netty
import io.ktor.server.engine.embeddedServer

fun startServer() = embeddedServer(Netty, 5000) { module() }.start(true)
