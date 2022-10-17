package todolist.pharos

import io.ktor.server.application.*
import com.example.customer.plugins.*
import todolist.pharos.plugins.configureRouting

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSerialization()
    configureRouting()
}