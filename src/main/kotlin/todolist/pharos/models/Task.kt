package todolist.pharos.models

import kotlinx.serialization.Serializable

@Serializable
class Task(
    val id: Int,
    var content: String,
    var check: Boolean,
    var position: Int,
    val priority: Int
)
