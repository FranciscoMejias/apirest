package todolist.pharos.dao

import io.ktor.server.application.*
import io.ktor.server.plugins.*
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

    private val readTaskFile: List<Task> get() =
        Json.decodeFromString(taskFilePath.readText())

    private fun newId() = readTaskFile.maxBy { it.id }.id + 1

    private fun rewriteTaskJson(list: List<Task>) =
        taskFilePath.writeText(list.encodeToJson())

    private fun List<Task>.encodeToJson() = Json.encodeToString(this)

    private  fun List<Task>.exist(task: Task) = this.find { it.id == task.id }

    /**
     * @return the json file content
     */
    fun getAll(): List<Task> = readTaskFile
    /**
     * @param id unique id to identify inside the tasks
     * @param list list inside the json file
     * @return the task relative to the id
     */
    fun getFromId(id: Int) = readTaskFile.find { it.id == id }

    /**
     * @param task reference to task Class
     * @param taskList list of tasks decoded from json file
     */
    fun create(task: Task) =
        if (taskListIsEmpty){
            taskFilePath.writeText(Json.encodeToString(listOf(task)))
            true
        } else {
            val taskList = readTaskFile.toMutableList()
            taskList.add(task)
            rewriteTaskJson(taskList)
        }

    fun delete(id: Int): Boolean {
        val list = readTaskFile.toMutableList()
        return if (list.removeIf{ it.id == id }) {
            rewriteTaskJson(list)
            true
        } else {
            false
        }
    }

    fun update(task: Task): Boolean {
        val list = readTaskFile.toMutableList()
        TODO()
    }
}

