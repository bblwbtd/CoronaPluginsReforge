package database

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.ktorm.database.Database

const val sqliteDrive = "org.sqlite.JDBC"

fun connectDB(
    driver: String,
    url: String,
    user: String? = null,
    password: String? = null,
    maxStatement: Int = 180
): Database {
    val cpds = ComboPooledDataSource()
    cpds.driverClass = driver
    cpds.jdbcUrl = url
    cpds.user = user
    cpds.password = password
    cpds.maxStatements = maxStatement

    return Database.connect(cpds)
}

fun initSQLiteDB(path: String?): Database {
    return if (path != null) {
        initSQLiteFromFile(path)
    } else {
        initSQLiteFromMemory()
    }
}

private fun initSQLiteFromFile(path: String): Database {
    return connectDB(sqliteDrive, "jdbc:sqlite:${path}")
}

private fun initSQLiteFromMemory(): Database {
    return connectDB(sqliteDrive, "jdbc:sqlite::memory:")
}
