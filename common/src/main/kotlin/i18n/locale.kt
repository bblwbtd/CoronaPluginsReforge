package xyz.ldgame.corona.common.i18n

import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Paths
import java.util.*


fun String.translate(sender: CommandSender? = null, variables: Map<String, String> = emptyMap()): String {
    val local = if (sender is Player) sender.locale else "en"

    return getText(this, local, variables)

}

fun getText(key: String, locale: String = "en", variables: Map<String, String> = emptyMap()): String {
    val bestMatchKey = strings.keys.maxByOrNull { it.commonPrefixWith(locale).length } ?: "en"
    val localizedText = strings[bestMatchKey]?.get(key) ?: strings["en"]?.get(key) ?: key

    if (variables.isNotEmpty()) {
        val sb = StringBuilder(localizedText)
        variables.forEach { (k, v) ->
            val placeholder = "$$k"
            var index = sb.indexOf(placeholder)
            while (index != -1) {
                sb.replace(index, index + placeholder.length, v)
                index = sb.indexOf(placeholder, index + v.length)
            }
        }
        return sb.toString()
    }

    return localizedText
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

