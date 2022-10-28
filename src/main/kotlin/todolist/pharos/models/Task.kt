package todolist.pharos.models

import kotlinx.serialization.Serializable

@Serializable
class Task(
    val id: Int,
    var content: String,
    var check: Boolean,
    var position: Int,
    var priority: Int,
) {
    fun changeTaskParameters(content: String, check: Boolean, position: Int, priority: Int) {
        this.content = content
        this.check = check
        this.position = position
        this.priority = priority
    }
}