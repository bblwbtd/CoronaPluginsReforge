package xyz.ldgame.corona.common.utils

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class Storage(private val dir: String) {

    init {
        File(dir).mkdirs()
    }

    fun readData(fileName: String): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(File(dir, "$fileName.yml"))
    }

    fun writeData(fileName: String, data: YamlConfiguration) {
        data.save(File(dir, "$fileName.yml"))
    }
}