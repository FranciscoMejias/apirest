package todolist.pharos.dao

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import todolist.pharos.models.Task
import todolist.pharos.models.TaskData
import java.nio.file.Path
import kotlin.io.path.*


/**
 * @author Pharos
 * @since 1.0.1
 */
class TemporalDao(
    private val dadesPath : Path = Path("dades"),
    private val taskFilePath : Path = Path(dadesPath.pathString, "task.json")
) {

    private val taskListIsEmpty get() = readTaskFile.isEmpty()

    init {
        if (dadesPath.notExists()) {
            dadesPath.createDirectory()
        }
        if (taskFilePath.notExists()) {
            taskFilePath.createFile()
            taskFilePath.writeText(Json.encodeToString(listOf<Task>()))
        }
    }

    private val readTaskFile: List<Task> get() =
        Json.decodeFromString(taskFilePath.readText())

    private fun newId() = readTaskFile.maxBy { it.id }.id + 1

    private fun rewriteTaskJson(list: List<Task>) =
        taskFilePath.writeText(list.encodeToJson())

    private fun List<Task>.encodeToJson() = Json.encodeToString(this)

    /**
     * @return All the values of Task class
     */
    fun getAll(): List<Task> = readTaskFile

    /**
     * @param id Unique id to identify inside the tasks
     * @return The task id
     */
    fun getFromId(id: Int) = readTaskFile.find { it.id == id }

    /**
     * @param taskData Reference to taskData Class
     * @return A task
     */
    private fun taskCreation(taskData: TaskData): Task =
        Task(
            newId(),
            taskData.content,
            taskData.check,
            taskData.position,
            taskData.priority
        )

    /**
     * @param taskData Reference to taskData Class
     * @return An empty list if not exists
     * @return A list of tasks if the list already exists
     */
    fun create(taskData: TaskData): Task {
        return if (taskListIsEmpty){
            val task = Task(0,
                taskData.content,
                taskData.check,
                taskData.position,
                taskData.priority)
            taskFilePath.writeText(Json.encodeToString(listOf(task)))
            task
        } else {
            val task = taskCreation(taskData)
            val taskList = readTaskFile.toMutableList()
            taskList.add(task)
            rewriteTaskJson(taskList)
            task
        }
    }

    /**
     * @param id Unique id to identify inside the tasks
     * @return True if the task is removed correctly
     * @return False if the task is not removed
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
     * @param task Reference to task Class
     * @return False if task is not found
     * @return True if the task was found and rewritten
     */
    fun update(task: Task): Boolean {
        return if (!readTaskFile.contains(task)) {
            val list = readTaskFile.toMutableList()
            list.find { it.id == task.id }?.changeTaskParameters(
                task.content,
                task.check,
                task.position,
                task.priority
            ) ?: return false
            rewriteTaskJson(list)
            true
        } else {
            false
        }
    }
}

