package todolist.pharos.models

import io.ktor.http.*
import kotlinx.serialization.*
import java.lang.IllegalArgumentException

/**
 *  Class for task that we receive from web page
 *  @author Pharos
 *  @param id Unique id to identify inside the dao
 *  @param content Text of the task
 *  @param check Boolean to know if task is checked
 *  @param position Position of the task inside task list
 *  @param priority Enum class that represent the priority
 *  @constructor Has secondary constructor to have directly parameters
 *  @exception IllegalArgumentException When parameters transformation fail
 *  @exception NullPointerException When miss parameters
 *  @see Priority
 *  @since 1.1.0
 */

@Serializable
class Task(
    val id: Int,
    var content: String,
    var check: Boolean,
    var position: Int,
    var priority: Priority,
) {
    fun changeTaskParameters(content: String, check: Boolean, position: Int, priority: Task.Priority) {
        this.content = content
        this.check = check
        this.position = position
        this.priority = priority
    }

    constructor(id: Int, parameters: Parameters): this(
        id,
        parameters["content"]!!,
        parameters["check"]?.toBooleanStrict()!!,
        parameters["position"]?.toInt()!!,
        Priority.valueOf(parameters["priority"]!!)
    )

    /**
     * Enum class to represent the priority
     * @author Pharos
     * @property NONE Represent priority in "0"
     * @property LOW Represent priority in "1"
     * @property MEDIUM Represent priority in "2"
     * @property HIGH Represent priority in "3"
     * @since 1.0.0
     */
    @Serializable
    enum class Priority {
        @SerialName("0") NONE,
        @SerialName("1") LOW,
        @SerialName("2") MEDIUM,
        @SerialName("3") HIGH
    }
}