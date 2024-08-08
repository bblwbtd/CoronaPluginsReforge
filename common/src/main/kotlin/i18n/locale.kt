package xyz.ldgame.corona.common.i18n

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Paths
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

val strings = mutableMapOf<String, MutableMap<String, String>>()

fun JavaPlugin.copyResource(resourcePath: String, targetPath: String) {
    val inputStream = getResource(resourcePath)

    if (inputStream == null) {
        logger.warning("Resource $resourcePath not found.")
        return
    }

    val outputStream = File(targetPath).outputStream()
    inputStream.copyTo(outputStream)

    inputStream.close()
    outputStream.close()
}

fun JavaPlugin.saveAndLoadLanguageFiles(resourcePath: String) {
    val langFolder = Paths.get(dataFolder.parent, resourcePath).toFile()

    if (!langFolder.exists()) {
        langFolder.mkdirs()
    }

    if (!File(langFolder, "meta.yml").exists()) {
        copyResource("$resourcePath/meta.yml", "$langFolder/meta.yml")
    }

    val supportedLanguages = LinkedList<String>()

    val existedMeta = YamlConfiguration.loadConfiguration(File(langFolder, "meta.yml"))
    val bundleMeta = YamlConfiguration.loadConfiguration(getResource("$resourcePath/meta.yml")!!.reader())

    if (existedMeta.getInt("version") < bundleMeta.getInt("version")) {
        copyResource("$resourcePath/meta.yml", "$langFolder/meta.yml")
        supportedLanguages.addAll(bundleMeta.getStringList("languages"))

        for (lang in supportedLanguages) {
            copyResource("$resourcePath/$lang.yml", "$langFolder/$lang.yml")
        }
    } else {
        supportedLanguages.addAll(existedMeta.getStringList("languages"))
    }

    for (lang in supportedLanguages) {
        val file = File(langFolder, "$lang.yml")

        if (!file.exists()) {
            copyResource("$resourcePath/$lang.yml", "$langFolder/$lang.yml")
        }

        val langConfig = YamlConfiguration.loadConfiguration(file)
        val existLangConfig = strings[lang] ?: mutableMapOf()

        for (key in langConfig.getKeys(false)) {
            existLangConfig[key] = langConfig.getString(key)!!
        }

        strings[lang] = existLangConfig
    }
}

