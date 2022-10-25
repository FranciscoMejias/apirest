package todolist.pharos.dao

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import todolist.pharos.models.Task
import todolist.pharos.models.TaskData
import java.nio.file.Path

internal class TemporalDaoTest {
    val dao = TemporalDao(Path.of("testing"))
    private val randomId = (0..1000).random()

    @Test
    fun getAll() {
        val getAll = dao.getAll()
        assertEquals(listOf<Task>(), getAll)
    }

    fun post (){
        val task = Task(
            randomId,
            "Hacer la tarea",
            false,
            1,
            Task.Priority.NONE
        )
    }
    @Test
    fun getAllAfterPost() {
        post()
        val getAll = dao.getAll()
        assertEquals(1, getAll.size)
    }

    @Test
    fun getFromId() {
    }

    @Test
    fun create() {

    }
}