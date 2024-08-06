import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import utils.info
import utils.warn
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

abstract class CommonMain : JavaPlugin() {
    companion object {
        lateinit var plugin: JavaPlugin
    }



    override fun onEnable() {
        plugin = this
        info("$name enabled.")
        saveDefaultConfig()
    }

    fun registerListeners(vararg listener: Listener) {
        listener.forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }

    override fun onDisable() {
        warn("$name disabled.")
    }

    fun copyResourceToPluginDir(resourcePath: String, pluginDir: File) {
        val sourceFile = File(javaClass.classLoader.getResource(resourcePath)!!.file)
        val destFile = File(pluginDir, resourcePath)

        if (!destFile.parentFile.exists()) {
            destFile.parentFile.mkdirs()
        }

        FileInputStream(sourceFile).use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }
    }

    fun saveAndLoadLanguageFiles(vararg supportedLanguages: String) {
        val langDir = File(dataFolder, "lang")
        if (!langDir.exists()) {
            langDir.mkdirs()
        }

        for (lang in supportedLanguages) {

            if (File(langDir, "$lang.yml").exists()) {
                continue
            }

            copyResourceToPluginDir("$lang.yml", langDir)
        }

    }


}