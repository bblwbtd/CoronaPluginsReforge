import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

class MagicViewPlugin : JavaPlugin {
    constructor() : super()

    constructor(
        loader: JavaPluginLoader?,
        description: PluginDescriptionFile?,
        dataFolder: File?,
        file: File?
    ) : super(
        loader!!, description!!, dataFolder!!, file!!
    )

    override fun onEnable() {
        getCommand("view")?.apply {
            setExecutor(ViewCommandExecutor())
            tabCompleter = CommandCompleter(generateCommand())
        }
        server.pluginManager.registerEvents(MagicViewListener(), this)
    }


}