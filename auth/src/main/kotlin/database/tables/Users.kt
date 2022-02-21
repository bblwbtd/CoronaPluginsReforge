package database.tables

import database.entities.User
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Users : Table<User>("users") {
    val username = varchar("username").primaryKey().bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
}