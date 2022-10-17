package todolist.pharos.models

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Int, var content: String, var check: Boolean, var position: Int, val priority: Int)
