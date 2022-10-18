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
 * @since 1.0.0
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

    private fun readFile(): List<Task> =
        Json.decodeFromString(taskFilePath.readText())
    /**
     * @return the json file content
     */
    fun getAll(): List<Task> = readFile()
    /**
     * @param id unique id to identify inside the tasks
     * @param list list inside the json file
     * @return the task relative to the id
     */
    fun getFromId(id: Int): Task? {
        val list = readFile()
        return list.find { it.id == id }
    }

    /**
     * @param task reference to task Class
     * @param taskList list of tasks decoded from json file
     */
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
        if (taskListIsEmpty){
            null
        } else {
            val list2 = readFile()
            val getId = getFromId(id)
//            list2.filter { it.id && it.check }
        }
        TODO()
    }

    fun update(task: Task): Boolean {
TODO()
    }
}

