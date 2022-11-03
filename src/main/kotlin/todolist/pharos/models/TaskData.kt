package todolist.pharos.models

import kotlinx.serialization.Serializable

@Serializable
class TaskData(
    var body: String,
    var checked: Boolean,
    var pos: Int,
    var prio: Int
)