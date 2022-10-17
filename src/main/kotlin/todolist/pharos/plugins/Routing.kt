package todolist.pharos.plugins

import todolist.pharos.routes.customerRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        customerRouting()
    }
}
