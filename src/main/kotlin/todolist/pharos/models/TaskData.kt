package todolist.pharos.models

import kotlinx.serialization.Serializable

@Serializable
class TaskData(
    var content: String,
    var check: Boolean,
    var position: Int,
    var priority: Int
)