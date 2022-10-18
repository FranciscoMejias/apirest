package todolist.pharos.dao

import io.ktor.server.application.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import todolist.pharos.models.Task
import kotlin.io.path.*
import kotlin.io.path.Path

/**
 * @author Pharos
 * @param dadesPath path where we save the information files
 * @param taskFilePath file where we store the tasks
 * @param taskListIsEmpty check if the json is empty
 * @param customersListRead create a list inside the json file if it's empty
 */

class TemporalDao {
    private val dadesPath = Path("dades")
    private val taskFilePath = Path(dadesPath.pathString, "task.json")
//    private val userFilePath = Path(dadesPath.pathString, "users.json")  Este apartado es de cara al siguiente sprint a la hora de hace inicios de sesion.

    private val taskListIsEmpty get() = taskFilePath.readText().isEmpty()
    private val customersListRead: List<Task>? get() =
        if (taskListIsEmpty) {
            null
        } else {
            Json.decodeFromString(taskFilePath.readText())
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
        if (taskListIsEmpty){
            taskFilePath.writeText(Json.encodeToString(listOf(task)))
        } else {
            val taskList: MutableList<Task> = Json.decodeFromString(taskFilePath.readText())
            taskList.add(task)
            taskFilePath.writeText(Json.encodeToString(taskList))
        }
    }

    fun delete(id: Int): Boolean {
TODO()
    }

    fun update(task: Task): Boolean {
TODO()
    }
}

