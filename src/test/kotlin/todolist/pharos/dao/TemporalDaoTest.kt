package todolist.pharos.dao

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import todolist.pharos.models.Task
import todolist.pharos.models.TaskData
import java.nio.file.Path
import kotlin.io.path.*

internal class TemporalDaoTest {
    private val packageTesting = Path.of("testing")
    private val dao get() = TemporalDao(packageTesting.fileName)

    private fun removeIfExistTestingPackage() {
        if (packageTesting.exists()) {
            Path.of(packageTesting.pathString, "task.json").deleteIfExists()
        }
        packageTesting.deleteIfExists()
    }

    private fun post (){
        dao.create(TaskData(
            "Testing",
            false,
            1,
            Task.Priority.NONE
        ))
    }

    @Test
    fun getAll() {
        removeIfExistTestingPackage()
        val getAll = dao.getAll()
        assertEquals(listOf<Task>(), getAll)
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