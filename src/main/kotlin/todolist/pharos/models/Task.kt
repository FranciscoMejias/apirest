package todolist.pharos.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 *  Class for task that we receive from web page
 *  @author Pharos
 *  @param id Unique id to identify inside the dao
 *  @param content Text of the task
 *  @param check Boolean to know if task is checked
 *  @param position Position of the task inside task list
 *  @param priority Enum class that represent the priority in integers
 *  @see Priority
 *  @since 1.0.0
 */
@Serializable
class Task(
    val id: Int,
    var content: String,
    var check: Boolean,
    var position: Int,
    val priority: Priority
) {
    /**
     * Enum class to represent the priority in integers
     * @author Pharos
     *
     */
    enum class Priority(val value: Int) {
        NONE(0),
        LOW(1),
        MEDIUM(2),
        HIGH(3)
    }
}

fun main() {
    println(Json.encodeToString(Task(1, "content", true, 10, Task.Priority.HIGH)))
}
