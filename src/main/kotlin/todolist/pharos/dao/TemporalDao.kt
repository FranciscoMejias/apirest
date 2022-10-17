package todolist.pharos.dao

import todolist.pharos.models.Customer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.io.path.*
import kotlin.io.path.Path

class TemporalDao {
    private val tmpPath = Path("")
    private val customerPath = Path(tmpPath.pathString, "customers.json")

    private val customersListIsEmpty get() = customerPath.readText().isEmpty()
    private val customersListRead: List<Customer>? get() =
        if (customersListIsEmpty) {
            null
        } else {
            Json.decodeFromString(customerPath.readText())
        }

    init {
        if (!customerPath.exists()) {
            customerPath.createFile()
        }
    }

    fun post(customer: Customer) {
        if (customerPath.readText().isEmpty()){
            customerPath.writeText(Json.encodeToString(listOf(customer)))
        } else {
            val customers: MutableList<Customer> = Json.decodeFromString(customerPath.readText())
            customers.add(customer)
            customerPath.writeText(Json.encodeToString(customers))
        }
    }

    fun getAll(): List<Customer>? = customersListRead
}

