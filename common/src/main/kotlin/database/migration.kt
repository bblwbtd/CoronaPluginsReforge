package database

import org.bukkit.plugin.Plugin
import org.flywaydb.core.Flyway

fun Plugin.migrateDB(vararg location: String) {
    val dataSource = getDataSource()
    val flyway = Flyway.configure().dataSource(dataSource).locations(*location).load()
    flyway.migrate()
}