import commands.Executor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        getCommand("auth")!!.setExecutor(Executor())
    }
}