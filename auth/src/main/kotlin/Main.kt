import commands.Executor
import org.bukkit.plugin.java.JavaPlugin
import utils.info
import utils.warn

class Main : JavaPlugin() {
    override fun onEnable() {
        info("CoronaAuth enabled")
        getCommand("auth")!!.setExecutor(Executor())
    }

    override fun onDisable() {
        warn("CoronaAuth disabled")
    }
}


