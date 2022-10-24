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
                temporalDao.getAll()
            )
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
                "Bad Parameters",
                status = HttpStatusCode.BadRequest
            )
            call.respond(
                temporalDao.getFromId(id) ?: return@get call.respondText(
                    "Not Found",
                    status = HttpStatusCode.NotFound
                )
            )
        }
        post {

//            val task = try {
//                Task(temporalDao.newId(), call.receiveParameters())
//            } catch (e: RuntimeException) {
//                return@post call.respondText(
//                    "Bad Parameters",
//                    status = HttpStatusCode.BadRequest
//                )
//            }
//            temporalDao.create(task)
//            call.respond(
//                temporalDao.getFromId(task.id) ?: return@post call.respondText(
//                    "Internal Server Error",
//                    status = HttpStatusCode.InternalServerError
//                )
//            )
        }
        delete("{id?}") {
            val id = try {
                call.parameters["id"]?.toIntOrNull() ?: return@delete call.respondText(
                    "",
                    status = HttpStatusCode.BadRequest
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
//            val task = try {
//                Task(call.receiveParameters())
//            } catch (e: NullPointerException) {
//                return@put call.respondText(
//                    "Missing parameters",
//                    status = HttpStatusCode.BadRequest
//                )
//            } catch (e: IllegalArgumentException) {
//                return@put call.respondText(
//                    "Failed at parse parameters",
//                    status = HttpStatusCode.BadRequest
//                )
//            }
//            if (temporalDao.update(task)) {
//                call.respondText(
//                    "Updates successfully",
//                    status = HttpStatusCode.OK
//                )
//            } else {
//            }
        }
    }
}
