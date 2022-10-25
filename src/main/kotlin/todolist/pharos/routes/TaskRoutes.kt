package todolist.pharos.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import todolist.pharos.dao.TemporalDao
import todolist.pharos.models.Task
import todolist.pharos.models.TaskData

fun Route.taskRouting() {
    val temporalDao = TemporalDao()

    route("/task") {
        get {
            call.respond(
                temporalDao.getAll()
            )
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.badRequest()
            call.respond(
                temporalDao.getFromId(id) ?: return@get call.respondText(
                    "Not Found",
                    status = HttpStatusCode.NotFound
                )
            )
        }
        post {
            try {
                val taskData = call.receive<TaskData>()
                call.respond(
                    temporalDao.create(taskData)
                )
            } catch (e: ContentTransformationException) {
                return@post call.badRequest()
            }
        }
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.badRequest()
            temporalDao.delete(id)
        }
        put("{id?}") {
            try {
                val task = call.receive<Task>()
                call.respondText(
                    "Updates successfully",
                    status = HttpStatusCode.OK
                )
            } catch (e: ContentTransformationException) {
                return@put call.badRequest()
            }
        }
    }
}

suspend fun ApplicationCall.badRequest() =
    this.respondText(
        "Bad Parameters",
        status = HttpStatusCode.BadRequest
    )