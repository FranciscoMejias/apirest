package todolist.pharos.routes

import todolist.pharos.dao.TemporalDao
import todolist.pharos.models.Customer
import todolist.pharos.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.taskRouting() {
    val temporalDao = TemporalDao()

    route("/task") {
        get {
            temporalDao.getAll() ?:
            call.respondText("No customers found", status = HttpStatusCode.OK)
        }
        get("{id?}") {
            temporalDao.getFromId() ?:
            call.respondText("No customer found", status = HttpStatusCode.OK)
        }
        post {
        }
        delete("{id?}") {
        }
        put("{id?}") {
        }
    }
}