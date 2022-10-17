package todolist.pharos.plugins

import todolist.pharos.routes.taskRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        taskRouting()
    }
}
