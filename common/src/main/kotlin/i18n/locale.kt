package xyz.ldgame.corona.common.i18n

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

fun String.locale(locale: CommandSender? = null): String {
    return getText(this, locale)
}

fun getText(rawText: String, locale: String = "en"): String {
    val bestMatchKey = strings.keys.maxByOrNull { key ->
        locale.commonPrefixWith(key).length
    } ?: "en"
    return strings[bestMatchKey]?.get(rawText) ?: strings["en"]?.get(rawText) ?: rawText
}

fun getText(rawText: String, sender: CommandSender? = null): String {
    if (sender is Player) {
        return getText(rawText, sender.locale)
    }

    if (sender is ConsoleCommandSender) {
        val currentLocal = Locale.getDefault()
        return getText(rawText, "${currentLocal.language}_${currentLocal.country}")
    }

    return getText(rawText, "en")
}

val strings = mutableMapOf<String, Map<String, String>>()

fun saveAndLoadLanguageFiles(plugin: JavaPlugin, vararg supportedLanguages: String) {
    for (lang in supportedLanguages) {
        if (File(plugin.dataFolder, "$lang.yml").exists()) {
            continue
        }
        plugin.saveResource("$lang.yml", false)
    }

    for (lang in supportedLanguages) {
        val file = plugin.dataFolder.resolve("$lang.yml")
        val langConfig = YamlConfiguration.loadConfiguration(file)
        strings[lang] = langConfig.getValues(false).mapValues { it.value.toString() }
    }
}
