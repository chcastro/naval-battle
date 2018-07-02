package com.naval.battle.main

import com.naval.battle.module.module
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun startServer() = embeddedServer(CIO, 5000) { module() }.start(true)
