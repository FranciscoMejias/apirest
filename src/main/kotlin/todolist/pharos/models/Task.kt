package todolist.pharos.models

import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import todolist.pharos.routes.toBoolean
import kotlin.properties.Delegates

/**
 *  Class for task that we receive from web page
 *  @author Pharos
 *  @param id Unique id to identify inside the dao
 *  @param content Text of the task
 *  @param check Boolean to know if task is checked
 *  @param position Position of the task inside task list
 *  @param priority Enum class that represent the priority
 *  @see Priority
 *  @since 1.0.0
 */
@Serializable
class Task(
    var id: Int,
    var content: String,
    var check: Boolean,
    var position: Int,
    var priority: Priority
) {
    constructor(parameters: Parameters) : super(this) {
        var secondContent: String by Delegates.notNull()
        var secondCheck: Boolean by Delegates.notNull()
        var secondPosition: Int by Delegates.notNull()
        var secondPriority: Priority by Delegates.notNull()
        parameters.forEach { name, valueList ->
            val value = valueList.toString()
            when(name) {
                "content" -> secondContent = value
                "check" -> secondCheck = value.toBoolean()
                "position" -> secondPosition = value.toInt()
                "priority" -> secondPriority = Priority.valueOf(value)
            }
        }
        this.id = 1
        this.content = secondContent
        this.check = secondCheck
        this.position = secondPosition
        this.priority = secondPriority
    }

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
        @SerialName("0") NONE(),
        @SerialName("1") LOW(),
        @SerialName("2") MEDIUM(),
        @SerialName("3") HIGH()
    }
}
