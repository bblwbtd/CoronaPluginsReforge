package database

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.ktorm.database.Database
import java.io.File
import javax.sql.DataSource

const val sqliteDrive = "org.sqlite.JDBC"

fun Plugin.connectDB(): Database {
    getDataSource().let {
        return Database.connect(it)
    }
}

fun Plugin.getDataSource(): DataSource {
    val defaultURL = "jdbc:sqlite:${
        File(dataFolder.apply { mkdirs() }, "data.db").apply {
            createNewFile()
        }.path
    }"

    val dbConfig = this.config.getConfigurationSection("database") ?: YamlConfiguration().apply {
        addDefaults(
            mapOf(
                "driver" to sqliteDrive,
                "url" to defaultURL
            )
        )
    }
    val driver = dbConfig.getString("driver", sqliteDrive)
    val url = dbConfig.getString("url", defaultURL)
    val username = dbConfig.getString("username")
    val pwd = dbConfig.getString("password")
    val maxState = dbConfig.getInt("maxStatements", 180)

    return ComboPooledDataSource().apply {
        driverClass = driver
        jdbcUrl = url
        user = username
        password = pwd
        maxStatements = maxState
    }
}
