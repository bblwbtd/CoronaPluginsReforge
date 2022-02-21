package database

import org.bukkit.plugin.Plugin
import org.flywaydb.core.Flyway

fun Plugin.migrateDB() {
    val dataSource = getDataSource()
    val flyway = Flyway.configure().dataSource(dataSource).load()
    flyway.migrate()
}