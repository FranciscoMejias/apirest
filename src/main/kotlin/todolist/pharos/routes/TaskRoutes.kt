package todolist.pharos.routes

import todolist.pharos.dao.TemporalDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import todolist.pharos.models.Task
import kotlin.properties.Delegates

fun Route.taskRouting() {
    val temporalDao = TemporalDao()

    route("/task") {
        get {
            call.respond(
                temporalDao.getAll().ifEmpty {
                    return@get call.respondText("No customers found", status = HttpStatusCode.OK)
                }
            )
        }
        get("{id?}") {
            val id = try {
                call.parameters["id"]?.toInt() ?: return@get call.respondText(
                    "Missing Id",
                    status = HttpStatusCode.NoContent
                )
            } catch (e: IllegalArgumentException) {
                return@get call.respondText(
                    "Id not a integer",
                    status = HttpStatusCode.BadRequest
                )
            }
            if (id < 1) {
                return@get call.respondText(
                    "Id can't be 0 or negative",
                    status = HttpStatusCode.BadRequest
                )
            }
            call.respond(
                temporalDao.getFromId(id) ?: return@get call.respondText(
                    "No task found",
                    status = HttpStatusCode.NotFound
                )
            )
        }
        post {
            val task = try {
                Task(temporalDao.newId(), call.receiveParameters())
            } catch (e: IllegalArgumentException) {
                return@post call.respondText(
                    "Failed at parse parameters",
                    status = HttpStatusCode.BadRequest
                )
            }
            temporalDao.create(task)
            call.respond(
                temporalDao.getFromId(task.id) ?: return@post call.respondText(
                    "Internal Server Error",
                    status = HttpStatusCode.InternalServerError
                )
            )
        }
        delete("{id?}") {
            val id = try {
                call.parameters["id"]?.toInt() ?: return@delete call.respondText(
                    "Missing Id",
                    status = HttpStatusCode.NoContent
                )
            } catch (e: IllegalArgumentException) {
                return@delete call.respondText(
                    "Id not a integer",
                    status = HttpStatusCode.BadRequest
                )
            }
            temporalDao.delete(id)
        }
        put("{id?}") {

            val task = try {
                Task(id, call.receiveParameters())
            } catch (e: IllegalArgumentException) {
                return@put call.respondText(
                    "Failed at parse parameters",
                    status = HttpStatusCode.BadRequest
                )
            }
        }
    }
}
