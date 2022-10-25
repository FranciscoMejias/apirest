package todolist.pharos.dao

import io.ktor.server.plugins.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import todolist.pharos.models.Task
import todolist.pharos.models.TaskData
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.io.path.Path


/**
 * @author Pharos
 * @since 1.0.0
 */

class TemporalDao(
    private val dadesPath : Path = Path("dades"),
    private val taskFilePath : Path = Path(dadesPath.pathString, "task.json")
) {

    private val taskListIsEmpty get() = taskFilePath.readText().isEmpty()

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

    fun newId() = readTaskFile.maxBy { it.id }.id + 1

    private fun rewriteTaskJson(list: List<Task>) =
        taskFilePath.writeText(list.encodeToJson())

    private fun List<Task>.encodeToJson() = Json.encodeToString(this)

    private fun taskAlreadyExists (task: Task) =
        readTaskFile.find { it.id ==  task.id}

    /**
     * @return All the values of Task class
     */
    fun getAll(): List<Task> = readTaskFile

    /**
     * @param id unique id to identify inside the tasks
     * @return the task id
     */
    fun getFromId(id: Int) = readTaskFile.find { it.id == id }

    /**
     * @param task reference to task Class
     * @return an empty list if not exists
     * @return a list of tasks if the list already exists
     */


    fun taskCreation(taskData: TaskData) : Task{
        val task = Task(
            newId(),
            taskData.content,
            taskData.check,
            taskData.position,
            taskData.priority
        )
        return task
    }

    fun create(taskData: TaskData): Task {
        val task = taskCreation(taskData)
        if (taskListIsEmpty){
            taskFilePath.writeText(Json.encodeToString(listOf(task)))
        } else {
            val taskList = readTaskFile.toMutableList()
            taskList.add(task)
            rewriteTaskJson(taskList)
        }
        return task
    }

    /**
     * @param id unique id to identify inside the tasks
     * @return true if the task is removed correctly
     * @return false if the task is not removed
     */
    fun delete(id: Int): Boolean {
        val list = readTaskFile.toMutableList()
        return if (list.removeIf{ it.id == id }) {
            rewriteTaskJson(list)
            true
        } else {
            false
        }
    }

    /**
     * @param task reference to task Class
     * @return false if task is not found
     * @return true if the task was found and rewritten

     */
    fun update(task: Task): Boolean {
        val list = readTaskFile.toMutableList()
        taskAlreadyExists(task)?.let {
            it.changeTaskParameters(task.content,
                    task.check,
                    task.position,
                    task.priority)
        }
        rewriteTaskJson(list)
        return true
    }

}

