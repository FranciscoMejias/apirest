package todolist.pharos.dao

import io.ktor.server.application.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import todolist.pharos.models.Task
import kotlin.io.path.*
import kotlin.io.path.Path

class TemporalDao {
    private val dadesPath = Path("dades")
    private val taskFilePath = Path(dadesPath.pathString, "task.json")

    private val taskListIsEmpty get() = taskFilePath.readText().isEmpty()
    private val customersListRead: List<Customer>? get() =
        if (taskListIsEmpty) {
            null
        } else {
            Json.decodeFromString(customerPath.readText())
        }

    init {
        if (dadesPath.notExists()) {
            dadesPath.createDirectory()
        }
        if (taskFilePath.notExists()) {
            taskFilePath.createFile()
        }
    }

    fun getAll(): List<Task> = readFile()

    private fun readFile(): List<Task> =
        Json.decodeFromString(taskFilePath.readText())

    fun getFromId(id: Int): Task? {
        val list = readFile()
        return list.find { it.id == id }
    }

    fun create(task: Task) {
        if (customerPath.readText().isEmpty()){
            customerPath.writeText(Json.encodeToString(listOf(customer)))
        } else {
            val customers: MutableList<Customer> = Json.decodeFromString(customerPath.readText())
            customers.add(customer)
            customerPath.writeText(Json.encodeToString(customers))
        }
    }

    fun delete(id: Int): Boolean {

    }

    fun update(task: Task): Boolean {

    }
}

