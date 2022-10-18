package todolist.pharos.routes

import todolist.pharos.dao.TemporalDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import todolist.pharos.models.Task

fun Route.taskRouting() {
    val temporalDao = TemporalDao()

    route("/task") {
        get {
            temporalDao.getAll() ?:
            call.respondText("No customers found", status = HttpStatusCode.OK)
        }
        get("{id?}") {
            val id =
                call.parameters["id"]?.toInt() ?: return@get call.respondText(
                    "Missing Id",
                    status = HttpStatusCode.BadRequest
                )
            val task =
                temporalDao.getFromId(id) ?: return@get call.respondText(
                    "No task found",
                    status = HttpStatusCode.NotFound
                )
            call.respond(task)
        }
        post {
            val task = call.receive<Task>()
            temporalDao.create(task)
        }
        delete("{id?}") {
            val id = 1
            temporalDao.delete(id)
        }
        put("{id?}") {
            val task = call.receive<Task>()
            temporalDao.update(task)
        }
    }
}