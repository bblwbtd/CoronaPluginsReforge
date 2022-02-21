package database

import database.tables.Users
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf

lateinit var db: Database

val Database.users get() = this.sequenceOf(Users)
