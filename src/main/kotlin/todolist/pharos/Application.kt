package todolist.pharos

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import todolist.pharos.plugins.configureRouting
import todolist.pharos.plugins.configureSerialization

fun main() {
    val applicationTask = ApplicationTask()
    applicationTask.start()
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
    }
}

class ApplicationTask {
    fun start() {
        embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
            module()
        }.start(wait = true)
    }
}