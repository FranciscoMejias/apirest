package todolist.pharos.models

import kotlinx.serialization.Serializable

@Serializable
class Task(
    val id: Int,
    var body: String,
    var checked: Boolean,
    var pos: Int,
    var prio: Int,
) {
    fun changeTaskParameters(body: String, checked: Boolean, pos: Int, prio: Int) {
        this.body = body
        this.checked = checked
        this.pos = pos
        this.prio = prio
    }
}