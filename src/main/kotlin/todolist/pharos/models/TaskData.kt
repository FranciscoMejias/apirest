package todolist.pharos.models


class TaskData(
    var content: String,
    var check: Boolean,
    var position: Int,
    var priority: Task.Priority,
)